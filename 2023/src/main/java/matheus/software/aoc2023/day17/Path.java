package matheus.software.aoc2023.day17;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public final class Path {

    private Integer[][] cities;
    private LinkedList<int[]> coordinates;

    public Path(Integer[][] thisCities, int[] start) {
        this.cities = thisCities;
        this.coordinates = new LinkedList<>();
        this.coordinates.add(start);
    }

    public Path(Path original, int[] next) {
        this.cities = original.getCities();
        this.coordinates = new LinkedList<>();
        this.coordinates.addAll(original.getCoordinates());
        this.coordinates.add(next);
    }

    public int getHeatLoss() {
        int[] first = this.getCoordinates().getFirst();
        int firstHeat = this.getCities()[first[0]][first[1]];
        return this.getCoordinates().stream()
                .map(i -> {
                    try {
                        return this.getCities()[i[0]][i[1]];
                    } catch(ArrayIndexOutOfBoundsException e) {
                        throw e;
                    }
                })
                .reduce(-firstHeat, Integer::sum);
    }

    public boolean notFinished(int lastRow, int lastColumn) {
        int[] tail = this.getTail();
        return !(tail[0] == lastRow
                && tail[1] == lastColumn);
    }

    public int[] getTail() {
        return this.getCoordinates().getLast();
    }

    private boolean lastThreeSame(final int i, final int where) {
//        return false;
        List<int[]> coordinates = this.getCoordinates();
        int length = this.getCoordinates().size();
        if (length < 3) {
            return false;
        }
        return coordinates.get(length - 1)[where] == i
                && coordinates.get(length - 2)[where] == i
                && coordinates.get(length - 3)[where] == i;
    }

    private boolean lastTwoSame(final int i, final int where) {
//        return false;
        List<int[]> coordinates = this.getCoordinates();
        int length = this.getCoordinates().size();
        if (length < 2) {
            return false;
        }
        return coordinates.get(length - 1)[where] == i
                && coordinates.get(length - 2)[where] == i;
    }

    public List<Path> getNextPaths() {
        List<Path> newones = new LinkedList<>();
        int[] tail = this.getTail();
        int endRow = this.getCities().length - 1;
        int endColumn = this.getCities()[endRow].length - 1;
        if (tail[0] > 0 && !lastThreeSame(tail[1], 1)) {
            newones.add(new Path(this, new int[]{tail[0] - 1, tail[1]}));
        }
        if (tail[0] < endRow && !lastThreeSame(tail[1], 1)) {
            newones.add(new Path(this, new int[]{tail[0] + 1, tail[1]}));
        }
        if (tail[1] > 0 && !lastThreeSame(tail[0], 0)) {
            newones.add(new Path(this, new int[]{tail[0], tail[1] - 1}));
        }
        if (tail[1] < endColumn && !lastThreeSame(tail[0], 0)) {
            newones.add(new Path(this, new int[]{tail[0], tail[1] + 1}));
        }
        return newones;
    }

    public boolean tooLarge() {
        int endRow = this.getCities().length;
        long endColumn = this.getCities()[endRow - 1].length;
        return this.getCoordinates().size() > ((endColumn * endRow));
    }

    public boolean hasCircle() {
        HashSet<String> coordinateHashes = new HashSet<>();
        for (int[] coordinate: this.getCoordinates()) {
            coordinateHashes.add(coordinate[0] + "_" + coordinate[1]);
        }
        return coordinateHashes.size() < this.getCoordinates().size();
    }

    @Override
    public String toString() {
        return this.coordinates.size() + " " + this.getHeatLoss();
    }

    public int getSize() {
        return this.getCoordinates().size();
    }

    public int getSortValue(int maxHeat, int maxSize) {
        int sortValue = 0;
        if (this.getHeatLoss() > maxHeat) {
            sortValue -= 1;
        }
        if (this.getHeatLoss() < maxHeat) {
            sortValue += 1;
        }
//        if (this.getSize() > maxSize) {
//            sortValue += 1;
//        }
//        if (this.getSize() < maxSize) {
//            sortValue -= 1;
//        }
        return sortValue;
    }

    public boolean largeThan(Path[][] mins) {
        int[] tail = this.getTail();
        return mins[tail[0]][tail[1]].getHeatLoss() < this.getHeatLoss();
    }

    public int relativeLoss(Path[][] mins) {
        int[] tail = this.getTail();
        return this.getHeatLoss() - mins[tail[0]][tail[1]].getHeatLoss();
    }

    public boolean hotterThan(Path minPath) {
        if (minPath == null) {
            return false;
        }
        return this.getHeatLoss() > minPath.getHeatLoss();
    }

    public double closeToGoal(int lastRow, int lastColumn) {
        int[] tail = getTail();
        return Math.pow(tail[0] - lastRow, 2) + Math.pow(tail[1] - lastColumn, 2);
    }

    public int futureHeatLoss(int lastRow, int lastColumn) {
        LinkedList<Path> paths = new LinkedList<>();
        paths.add(this);
        while (paths.getFirst().notFinished(lastRow, lastColumn)) {
            paths.addAll(paths.removeFirst().getNextPaths());
            paths.sort(Comparator.comparingInt(Path::getSize));
        }
        return paths.getFirst().getSize() + this.getHeatLoss();
//        return ((int) Math.sqrt(closeToGoal(lastRow, lastColumn)))*3 + this.getHeatLoss();
    }

    public boolean totallyContains(Path path) {
        if (this == path || this.getSize() == path.getSize()) return false;
        Set<String> positions = this.getCoordinates().stream().map(i -> i[0] + "_" + i[1]).collect(Collectors.toSet());
        return path.getCoordinates().stream().allMatch(i -> positions.contains(i[0] + "_" + i[1]));
//        return this.getCoordinates().stream().anyMatch(i -> path.getTail()[0] == i[0] && path.getTail()[1] == i[1]);
    }

    public boolean contains(int[] position) {
        return this.getCoordinates().stream().anyMatch(i -> i[0] == position[0] && i[1] == position[1]);
    }

    public String getKey() {
        int[] tail = this.getTail();
        int group = 1;
        if (lastThreeSame(tail[0],0) || lastThreeSame(tail[1],1)) {
            group = 3;
        } else if (lastTwoSame(tail[0],0) || lastTwoSame(tail[1],1)) {
            group = 2;
        }
        return tail[0] + "_" + tail[1] + "_" + group;
//        return "fixed";
    }
}
