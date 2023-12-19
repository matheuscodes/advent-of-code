package matheus.software.aoc2023.day19;

import java.util.HashMap;

import static java.lang.Integer.parseInt;

public final class MachinePart {

    private final HashMap<String, Integer> ratings = new HashMap<>();
    public MachinePart(final String line) {
        var thisRatings = line.replaceAll("\\{", "")
                .replaceAll("}", "")
                .split(",");

        for (String rating: thisRatings) {
            String[] split = rating.split("=");
            this.ratings.put(split[0], parseInt(split[1]));
        }
    }

    public int getRating(final String category) {
        return this.ratings.get(category);
    }

    public long finalRating() {
        return ratings.values().stream().reduce(0, Integer::sum);
    }
}
