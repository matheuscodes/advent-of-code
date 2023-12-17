package matheus.software.aoc2023.day17;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public final class CityMap {
    public long leastHeatLoss(final String raw) {
        String[] lines = raw.split("\\n");
        Integer[][] cities = new Integer[lines.length][];
        Path[][] mins =  new Path[lines.length][];
        int lastRow = cities.length - 1;
        for (int i = 0; i < lines.length; i++) {
            cities[i] = Arrays.stream(lines[i].split(""))
                    .map(Integer::parseInt).toArray(Integer[]::new);
            mins[i] = new Path[cities[i].length];
            Arrays.fill(mins[i], null);
        }
        int lastColumn = cities[lastRow].length - 1;
        LinkedList<Path> paths = new LinkedList<>();
        paths.add(new Path(cities, new int[]{0, 0}));
        Path minPath = null;
        while (paths.size() > 0) {
            Path first = paths.removeFirst();
            System.out.println(first.getCoordinates().size() + " >> " + first.tooLarge() + " // " + paths.size() + " " + (minPath == null ? " " : minPath.getHeatLoss()));
            paths.addAll(first.getNextPaths());
            Path currentMinPath = minPath;
            paths.removeAll(
                    paths.stream()
                            .filter(i -> i.hasCircle() || i.tooLarge() || i.hotterThan(currentMinPath))
                            .toList()
            );
            paths.forEach(path -> {
               int[] tail = path.getTail();
               if (mins[tail[0]][tail[1]] == null || mins[tail[0]][tail[1]].getHeatLoss() > path.getHeatLoss()) {
                   mins[tail[0]][tail[1]] = path;
               }
            });
//            paths.removeAll(
//                    paths.stream()
//                            .filter(i -> i.largeThan(mins))
//                            .toList()
//            );
//            paths.sort(Comparator.comparingInt(i -> i.relativeLoss(mins)));
            paths.sort(Comparator.comparingInt(Path::getHeatLoss));
            paths.sort(Comparator.comparingDouble(i -> i.closeToGoal(lastRow, lastColumn)));
            if (paths.size() > 0) {
                Path thisMinPath = paths.getFirst();
                if (!thisMinPath.notFinished(lastRow, lastColumn)
                        && (minPath == null
                        || thisMinPath.getHeatLoss() < minPath.getHeatLoss())) {
                    minPath = thisMinPath;
                    paths.removeFirst();
                }
            }
        }
        return minPath.getHeatLoss();
    }

    public long leastHeatLoss2(final String raw) {
        String[] lines = raw.split("\\n");
        Integer[][] cities = new Integer[lines.length][];
        Path[][] mins =  new Path[lines.length][];
        int lastRow = cities.length - 1;
        for (int i = 0; i < lines.length; i++) {
            cities[i] = Arrays.stream(lines[i].split(""))
                    .map(Integer::parseInt).toArray(Integer[]::new);
            mins[i] = new Path[cities[i].length];
            Arrays.fill(mins[i], null);
        }
        int lastColumn = cities[lastRow].length - 1;
        mins[0][0] = new Path(cities, new int[]{0, 0});
        LinkedList<int[]> positions = new LinkedList<>();
        for (int i = 0; i <= lastRow; i++) {
            for (int j = 0; j <= lastColumn; j++) {
                positions.add(new int[]{i, j});
            }
        }
        positions.sort(Comparator.comparingDouble(i -> -closeToGoal(i, lastRow, lastColumn)));
        for (int[] position: positions) {
//            LinkedList<Path> paths = flattenMins(mins);
//            int initial = paths.size();
//            paths.removeAll(
//                    paths.stream()
//                            .filter(p -> paths.stream().anyMatch(i -> i.totallyContains(p)))
//                            .toList()
//            );
//            paths.removeAll(
//                    paths.stream()
//                            .filter(p -> p.contains(position))
//                            .toList()
//            );
//            System.out.println("Processing... " + paths.size() + "/" + initial + ":" + position[0] + "," + position[1]);
//            paths.sort(Comparator.comparingInt(Path::getHeatLoss));
            while (mins[position[0]][position[1]] == null) {
                LinkedList<Path> paths = flattenMins(mins);
                paths.sort(Comparator.comparingInt(Path::getHeatLoss));
                Path first = paths.removeFirst();
                paths.addAll(first.getNextPaths());
                paths.removeAll(
                        paths.stream()
                                .filter(p -> p.hasCircle() || p.tooLarge())
                                .toList()
                );
                var oldPaths = paths.clone();
//                paths.removeAll(
//                        paths.stream()
//                                .filter(p -> paths.stream().anyMatch(i -> i.totallyContains(p)))
//                                .toList()
//                );
                paths.forEach(path -> {
                    int[] tail = path.getTail();
                    if (mins[tail[0]][tail[1]] == null || mins[tail[0]][tail[1]].getHeatLoss() > path.getHeatLoss()) {
                        mins[tail[0]][tail[1]] = path;
                    }
                });
//                paths.sort(Comparator.comparingInt(i -> i.futureHeatLoss(position[0], position[1])));
//                paths.sort(Comparator.comparingDouble(i -> i.closeToGoal(lastRow, lastColumn)));
            }
//            if (mins[position[0]][position[1]] == null || mins[position[0]][position[1]].getHeatLoss() > paths.getFirst().getHeatLoss()) {
//                mins[position[0]][position[1]] = paths.getFirst();
//            }
        }
        return mins[lastRow][lastColumn].getHeatLoss();
    }

    private LinkedList<Path> flattenMins(Path[][] mins) {
        LinkedList<Path> toReturn = new LinkedList<>();
        for (int i = 0; i < mins.length; i++) {
            for (int j = 0; j < mins[i].length; j++) {
                if (mins[i][j] != null) {
                    toReturn.add(mins[i][j]);
                }
            }
        }
        return toReturn;
    }

    public double closeToGoal(int[] tail, int lastRow, int lastColumn) {
        return Math.pow(tail[0] - lastRow, 2) + Math.pow(tail[1] - lastColumn, 2);
    }

    public long leastHeatLoss3(String raw) {
        String[] lines = raw.split("\\n");
        Integer[][] cities = new Integer[lines.length][];
        HashMap<String, Path>[][] mins =  new HashMap[lines.length][];
        int lastRow = cities.length - 1;
        for (int i = 0; i < lines.length; i++) {
            cities[i] = Arrays.stream(lines[i].split(""))
                    .map(Integer::parseInt).toArray(Integer[]::new);
            mins[i] = new HashMap[cities[i].length];
            for (int k = 0; k < mins[i].length; k++) {
                mins[i][k] = new HashMap<>();
            }
        }
        int lastColumn = cities[lastRow].length - 1;

        LinkedList<Path> openSet = new LinkedList<>();

        Path start = new Path(cities, new int[]{0, 0});
        mins[0][0].put(start.getKey(), start);
        openSet.add(start);
        HashSet<String> dones = new HashSet<>();
        int min = Integer.MAX_VALUE;
        while (openSet.size() > 0) {
            openSet.sort(Comparator.comparingInt(Path::getHeatLoss));
            Path current = openSet.removeFirst();
            if (!current.notFinished(lastRow, lastColumn)) {
                if (mins[lastRow][lastColumn].get(current.getKey()) == null || mins[lastRow][lastColumn].get(current.getKey()).getHeatLoss() > current.getHeatLoss()) {
                    mins[lastRow][lastColumn].put(current.getKey(), current);
//                    break;
                }
                if(current.getHeatLoss() < min) {
                    min = current.getHeatLoss();
                }
            } else {
                current.getNextPaths().forEach(path -> {
                    int[] tail = path.getTail();
                    if (mins[tail[0]][tail[1]].get(path.getKey()) == null || mins[tail[0]][tail[1]].get(path.getKey()).getHeatLoss() > path.getHeatLoss()) {
                        mins[tail[0]][tail[1]].put(path.getKey(), path);
                    }
                    if (!path.hasCircle() && !dones.contains(path.getKey())) {
                        openSet.add(path);
                        dones.add(path.getKey());
                    }
                });
            }

            int thisMin = min;
            openSet.removeIf(i -> i.getHeatLoss() > thisMin);
        }

        return mins[lastRow][lastColumn].values().stream().map(Path::getHeatLoss).reduce(Integer.MAX_VALUE, Integer::min);
    }

    //https://www.redblobgames.com/pathfinding/a-star/introduction.html
    public long leastHeatLoss4(String raw) {
        String[] lines = raw.split("\\n");
        Integer[][] cities = new Integer[lines.length][];
        HashMap<String, Path>[][] mins =  new HashMap[lines.length][];
        int lastRow = cities.length - 1;
        for (int i = 0; i < lines.length; i++) {
            cities[i] = Arrays.stream(lines[i].split(""))
                    .map(Integer::parseInt).toArray(Integer[]::new);
            mins[i] = new HashMap[cities[i].length];
            for (int k = 0; k < mins[i].length; k++) {
                mins[i][k] = new HashMap<>();
            }
        }
        int lastColumn = cities[lastRow].length - 1;

        var start = new Point(0,0);
        var goal = new Point(lastRow,lastColumn);
        HashMap<Point, Integer> frontier = new HashMap<>();
        frontier.put(start, 0);
        var came_from = new HashMap<Point, Point>();
        var cost_so_far = new HashMap<Point, Integer>();

        came_from.put(start, null);
        cost_so_far.put(start, 0);

        while (!frontier.isEmpty()) {
            var current = getPriority(frontier);
            frontier.remove(current);
            if(graphCost(current, cities) == 30) {
                System.out.println();
            }
            if (current.equals(goal)) {
//                break;
            } else {

            for (Point next: getNeighbours(current, lastRow, lastColumn, came_from)) {
                var new_cost = graphCost(next, cities);
                if (!cost_so_far.containsKey(next) || new_cost < cost_so_far.get(next)) {
                    cost_so_far.put(next, new_cost);
                    var priority = new_cost;// + heuristic(next, lastRow, lastColumn);
                    frontier.put(next, priority);
                    came_from.put(next, current);
                }
            }
            }
        }

        int[][] debug = new int[lastRow+1][lastColumn+1];
        for (int i = 0; i < debug.length; i++) {
            Arrays.fill(debug[i], Integer.MAX_VALUE);
        }
        cost_so_far.keySet().forEach(p -> {
            int value = cost_so_far.get(p);
            if(debug[p.i][p.j] > value) {
                debug[p.i][p.j] = value;
            }
        });
        var debug2 = cost_so_far.keySet().stream()
                .filter(i -> i.equals(goal))
                .map(i -> readPath(i))
                .toList();
        var dfuk = cost_so_far.keySet().stream()
                .filter(i -> i.equals(goal))
                .map(i -> graphCost(i,cities))
                .toList();
        return cost_so_far.keySet().stream()
                .filter(i -> i.equals(goal))
                .map(i -> cost_so_far.get(i))
                .reduce(Integer.MAX_VALUE, Integer::min);
    }

    private int graphCost(Point next, Integer[][] cities) {
        LinkedList<Point> path = readPath(next);
        int firstHeat = cities[path.getFirst().i][path.getFirst().j];
        int value = path.stream()
                .map(p -> cities[p.i][p.j])
                .reduce(-firstHeat, Integer::sum);
        return value;
    }

    public LinkedList<Point> readPath(Point i) {
        Stack<Point> newone = new Stack<>();
        Point next = i;
        while (next != null) {
            newone.push(next);
            next = next.from;
        }
        LinkedList<Point> reversed = new LinkedList<>();
        while (!newone.empty()) {
            reversed.add(newone.pop());
        }
        return reversed;
    }

    public int heuristic(Point p, int lastRow, int lastColumn) {
        return (int) Math.sqrt(Math.pow(p.i - lastRow, 2) + Math.pow(p.j - lastColumn, 2));
    }

    private List<Point> getNeighbours(Point current, int endRow, int endColumn, HashMap<Point, Point> path) {
        List<Point> newones = new LinkedList<>();
        if (current.i > 0
                && !("UPWARDS".equals(previousDirection(current, path))
                && "UPWARDS".equals(previousDirection(path.get(current), path)))) {
            if (!(current.from != null && (current.from.i == current.i - 1) && (current.from.j == current.j))) {
                newones.add(new Point(current.i - 1, current.j, current));
            }
        }
        if (current.i < endRow
                && !("DOWNWARDS".equals(previousDirection(current, path))
                && "DOWNWARDS".equals(previousDirection(path.get(current), path)))) {
            if (!(current.from != null && (current.from.i == current.i + 1) && (current.from.j == current.j))) {
                newones.add(new Point(current.i + 1, current.j, current));
            }
        }
        if (current.j > 0
                && !("LEFT".equals(previousDirection(current, path))
                && "LEFT".equals(previousDirection(path.get(current), path)))) {
            if (!(current.from != null && (current.from.i == current.i) && (current.from.j == current.j - 1))) {
                newones.add(new Point(current.i, current.j - 1, current));
            }
        }
        if (current.j < endColumn
                && !("RIGHT".equals(previousDirection(current, path))
                && "RIGHT".equals(previousDirection(path.get(current), path)))) {
            if (!(current.from != null && (current.from.i == current.i) && (current.from.j == current.j + 1))) {
                newones.add(new Point(current.i, current.j + 1, current));
            }
        }
        return newones;
    }

    private String previousDirection(Point current, HashMap<Point, Point> path) {
        if (path.get(current) == null) {
            return "START";
        }
        int i = current.i - path.get(current).i;
        int j = current.j - path.get(current).j;
        if (j == 0) {
            if (i > 0) {
                return "DOWNWARDS";
            } else {
                return "UPWARDS";
            }
        } else if (i == 0) {
            if (j > 0) {
                return "RIGHT";
            } else {
                return "LEFT";
            }
        } else {
            throw new RuntimeException("Impossible");
        }
    }

    private Point getPriority(HashMap<Point, Integer> frontier) {
        var list = new LinkedList<>(frontier.entrySet().stream().toList());
        list.sort(Comparator.comparingInt(Map.Entry::getValue));
        return list.get(0).getKey();
    }

    @Data
    public class Point {
        int i;
        int j;
        Point from = null;

        public Point(final int thisI, final int thisJ) {
            this.i = thisI;
            this.j = thisJ;
        }

        public Point(final int thisI, final int thisJ, final Point thisFrom) {
            this.i = thisI;
            this.j = thisJ;
            this.from = thisFrom;
        }
        @Override
        public String toString() {
//            return i + "," + j + "_" + ((from != null) ? from.i + "," + from.j : "");
            return i + "," + j + "_" + (from != null ? (from + "/" + (from.from != null ? from.from : "")) : "");
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                return ((Point) o).i == this.i && ((Point) o).j == this.j;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }
}
