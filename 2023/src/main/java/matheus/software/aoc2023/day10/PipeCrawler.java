package matheus.software.aoc2023.day10;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public final class PipeCrawler {

    private static final int X = 0;
    private static final int Y = 1;

    public List<int[]> farthestSteps(final String raw) {
        String[][] board = Arrays.stream(raw.split("\\n"))
                .map(i -> i.split("")).toArray(String[][]::new);

        int[] start = findStart(board);
        if (start == null) {
            return Collections.emptyList();
        }

        List<int[]> sides = findConnected(board, start, List.of(start));

        int[] nextA = sides.get(0);
        List<int[]> previousA = new ArrayList<>();
        previousA.add(start);
        do {
            previousA.add(nextA);
            List<int[]> candidatesA = findConnected(board, nextA, previousA);
            nextA = candidatesA.size() > 0 ? candidatesA.get(0) : null;
        } while (nextA != null);
        return previousA;
    }

    private List<int[]> findConnected(
            final String[][] board,
            final int[] point,
            final List<int[]> ignore
    ) {
        List<int[]> connected = new ArrayList<>();
        int[] north = new int[] {point[X], point[Y] - 1};
        int[] south = new int[] {point[X], point[Y] + 1};
        int[] east = new int[] {point[X] + 1, point[Y]};
        int[] west = new int[] {point[X] - 1, point[Y]};
        if (getFromBoard(board, north).matches("[F7|]")) {
            if (getFromBoard(board, point).matches("[S|LJ]")) {
                connected.add(north);
            }
        }

        if (getFromBoard(board, south).matches("[LJ|]")) {
            if (getFromBoard(board, point).matches("[S|F7]")) {
                connected.add(south);
            }
        }

        if (getFromBoard(board, east).matches("[J7-]")) {
            if (getFromBoard(board, point).matches("[S\\-FL]")) {
                connected.add(east);
            }
        }

        if (getFromBoard(board, west).matches("[FL-]")) {
            if (getFromBoard(board, point).matches("[S\\-J7]")) {
                connected.add(west);
            }
        }

        return connected.stream()
                .filter(i -> !listContains(ignore, i)).toList();
    }

    private String getFromBoard(final String[][] board, final int[] point) {
        try {
            return board[point[Y]][point[X]];
        } catch (ArrayIndexOutOfBoundsException e) {
            // Just ignore checks outside borders.
            return "";
        }
    }

    private int[] findStart(final String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals("S")) {
                    return new int[] {j, i};
                }
            }
        }
        return null;
    }

    public long enclosedArea(final String raw) {
        String[][] board = Arrays.stream(raw.split("\\n"))
                .map(i -> i.split("")).toArray(String[][]::new);
        List<int[]> loop = farthestSteps(raw);
        Set<int[]> inside = new HashSet<>();
        for (int y = 0; y < board.length; y++) {
            Set<int[]> lineInside = new HashSet<>();
            boolean isIn = false;
            String enterChar = " ";
            for (int x = 0; x < board[y].length; x++) {
                if (listContains(loop, new int[]{x, y})) {
                    if (" ".equals(enterChar)) {
                        enterChar = board[y][x];
                    }
                    if ("|".equals(board[y][x])) {
                        isIn = !isIn;
                        enterChar = " ";
                    }
                    if ("J".equals(board[y][x]) && "F".equals(enterChar)) {
                        isIn = !isIn;
                        enterChar = " ";
                    }
                    if ("J".equals(board[y][x]) && "L".equals(enterChar)) {
                        enterChar = " ";
                    }
                    if ("7".equals(board[y][x]) && "L".equals(enterChar)) {
                        isIn = !isIn; enterChar = " ";
                    }
                    if ("7".equals(board[y][x]) && "F".equals(enterChar)) {
                        enterChar = " ";
                    }

                    if (!lineInside.isEmpty()) {
                        inside.addAll(lineInside);
                        lineInside.clear();
                    }
                } else if (isIn) {
                    lineInside.add(new int[]{x, y});
                    enterChar = " ";
                }

            }
        }

        return inside.size();
    }

    private boolean listContains(final List<int[]> loop, final int[] item) {
        return loop.stream().anyMatch(i -> i[X] == item[X] && i[Y] == item[Y]);
    }
}
