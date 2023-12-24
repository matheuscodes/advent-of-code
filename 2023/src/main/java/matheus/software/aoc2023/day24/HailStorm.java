package matheus.software.aoc2023.day24;

import com.microsoft.z3.Context;
import com.microsoft.z3.Status;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;

import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;

@Component
public final class HailStorm {
    public long estimateCollisions(
            final String raw,
            final double min,
            final double max
    ) {
        var hails = Arrays.stream(raw.split("\\n"))
                .map(Hail::parseHail).toList();
        long count = 0;
        for (int i = 0; i < hails.size(); i++) {
            for (int j = i + 1; j < hails.size(); j++) {
                if (!hails.get(i).equals(hails.get(j))) {
                    var x = collisionXY(hails.get(i), hails.get(j));
                    var y = hails.get(i).yFromX(x);
                    var time1 = hails.get(i).getTime(x);
                    var time2 = hails.get(j).getTime(x);
                    if (x >= min && x <= max
                            && y >= min && y <= max
                            && time1 >= 0
                            && time2 >= 0) {
                        count += 1;
                    }
                }
            }
        }

        return count;
    }

    public double collisionXYZ(final Hail a, final Hail b) {
        var xy = collisionXY(a, b);
        var xz = collisionXZ(a, b);
        if (xy == xz) {
            return a.getTime(xz);
        }
        return POSITIVE_INFINITY;
    }

    private double collisionXY(final Hail a, final Hail b) {
        var numerator = (b.y - a.y)
                - ((b.x * b.vy) / b.vx)
                + ((a.x * a.vy) / a.vx);
        var denominator = (a.vy * b.vx - a.vx * b.vy) / (b.vx * a.vx);
        return numerator / denominator;
    }

    private double collisionXZ(final Hail a, final Hail b) {
        var numerator = (b.z - a.z)
                - ((b.x * b.vz) / b.vx)
                + ((a.x * a.vz) / a.vx);
        var denominator = (a.vz * b.vx - a.vx * b.vz) / (b.vx * a.vx);
        return numerator / denominator;
    }

    public long rockObliteration(final String raw) {
        val hails = Arrays.stream(raw.split("\\n"))
                .map(Hail::parseHail).toList();

        val opts = new HashMap<String, String>();
        opts.put("proof", "true");
        try (val ctx = new Context(opts)) {
            val solver = ctx.mkSolver();

            val x = ctx.mkIntConst("x");
            val y = ctx.mkIntConst("y");
            val z = ctx.mkIntConst("z");
            val vx = ctx.mkIntConst("vx");
            val vy = ctx.mkIntConst("vy");
            val vz = ctx.mkIntConst("vz");

            for (int i = 0; i < 3; i++) {
                val h = hails.get(i);
                val t = ctx.mkIntConst("t" + i);
                solver.add(ctx.mkEq(
                        ctx.mkAdd(x,
                                ctx.mkMul(vx, t)),
                        ctx.mkAdd(ctx.mkInt((long) h.x),
                                ctx.mkMul(ctx.mkInt((long) h.vx), t))));
                solver.add(ctx.mkEq(
                        ctx.mkAdd(y,
                                ctx.mkMul(vy, t)),
                        ctx.mkAdd(ctx.mkInt((long) h.y),
                                ctx.mkMul(ctx.mkInt((long) h.vy), t))));
                solver.add(ctx.mkEq(
                        ctx.mkAdd(z,
                                ctx.mkMul(vz, t)),
                        ctx.mkAdd(ctx.mkInt((long) h.z),
                                ctx.mkMul(ctx.mkInt((long) h.vz), t))));
            }

            if (solver.check() == Status.SATISFIABLE) {
                val test = solver.getModel()
                        .evaluate(ctx.mkAdd(x, ctx.mkAdd(y, z)), true);
                return parseLong(test.toString());
            }
        }

        return -1;
    }
    public record Hail(
            double x, double y, double z, double vx, double vy, double vz
    ) {
        public static Hail parseHail(final String toParse) {
            var split = toParse.split("@");
            var coordinates = split[0].trim().split(",");
            var velocities = split[1].trim().split(",");
            return new Hail(
                    parseDouble(coordinates[0].trim()),
                    parseDouble(coordinates[1].trim()),
                    parseDouble(coordinates[2].trim()),
                    parseDouble(velocities[0].trim()),
                    parseDouble(velocities[1].trim()),
                    parseDouble(velocities[2].trim())
            );
        }

        public double yFromX(final double givenX) {
            return y + ((vy * (givenX - x)) / (vx));
        }

        public double getTime(final double givenX) {
            return (givenX - x) / vx;
        }
    }
}
