package matheus.software.aoc2024.day15;

import org.springframework.stereotype.Component;

@Component
public final class Warehouse {

    public long gpsCoordinates(final String raw) {
        String[] split = raw.split("\\n\\n");
        String[] instructions = split[1].replaceAll("\\n", "").split("");
        String[] lines = split[0].split("\\n");
        String[][] warehouse = new String[lines.length][];
        int row = 0;
        for (String line: lines) {
            warehouse[row++] = line.split("");
        }
        int[] robot = null;
        for (int i = 0; i < warehouse.length; i++) {
            for (int j = 0; j < warehouse[i].length; j++) {
                if ("@".equals(warehouse[i][j])) {
                    robot = new int[]{i, j};
                }
            }
        }
        if (robot == null) {
            return -1;
        }


        for (String instruction: instructions) {
            if (!"@".equals(warehouse[robot[0]][robot[1]])) {
                throw new RuntimeException("Whatthefuck.");
            }
            int[] moved = move(warehouse, robot, instruction);
            if (moved != null) {
                robot = next(robot, instruction);
            }
        }

        long sum = 0;
        for (int i = 0; i < warehouse.length; i++) {
            for (int j = 0; j < warehouse[i].length; j++) {
                if ("O".equals(warehouse[i][j])) {
                    sum += i * 100L + j;
                }
            }
        }
        return sum;
    }

    public long expandedGpsCoordinates(final String raw) {
        String[] split = raw.split("\\n\\n");
        String[] instructions = split[1].replaceAll("\\n", "").split("");
        String[] lines = split[0]
                .replaceAll("\\.", "..")
                .replaceAll("@", "@.")
                .replaceAll("O", "[]")
                .replaceAll("#", "##")
                .split("\\n");
        String[][] warehouse = new String[lines.length][];
        int row = 0;
        for (String line: lines) {
            warehouse[row++] = line.split("");
        }
        int[] robot = null;
        for (int i = 0; i < warehouse.length; i++) {
            for (int j = 0; j < warehouse[i].length; j++) {
                if ("@".equals(warehouse[i][j])) {
                    robot = new int[]{i, j};
                }
            }
        }
        if (robot == null) {
            return -1;
        }

        for (String instruction: instructions) {
            if (!"@".equals(warehouse[robot[0]][robot[1]])) {
                throw new RuntimeException("Whatthefuck.");
            }
            int[] moved = movePlus(warehouse, robot, instruction);
            if (moved != null) {
                robot = next(robot, instruction);
            }
        }

        long sum = 0;
        for (int i = 0; i < warehouse.length; i++) {
            for (int j = 0; j < warehouse[i].length; j++) {
                if ("[".equals(warehouse[i][j])) {
                    sum += i * 100L + j;
                }
            }
        }
        return sum;
    }

    private int[] move(final String[][] warehouse, final int[] object, final String instruction) {
        int[] next = next(object, instruction);
        if (".".equals(warehouse[next[0]][next[1]])) {
            warehouse[next[0]][next[1]] = warehouse[object[0]][object[1]];
            warehouse[object[0]][object[1]] = ".";
            return object;
        } else if ("O".equals(warehouse[next[0]][next[1]])) {
            int[] moved = move(warehouse, next, instruction);
            if (moved != null) {
                warehouse[moved[0]][moved[1]] = warehouse[object[0]][object[1]];
                warehouse[object[0]][object[1]] = ".";
                return object;
            }
        }
        return null;
    }

    private int[] movePlus(final String[][] warehouse, final int[] object, final String instruction) {
        if (!isAbleToMove(warehouse, object, instruction)) {
            return null;
        }
        if ("#".equals(warehouse[object[0]][object[1]])) {
            return null;
        }
        if ("@".equals(warehouse[object[0]][object[1]])) {
            int[] next = next(object, instruction);
            if (".".equals(warehouse[next[0]][next[1]])) {
                warehouse[next[0]][next[1]] = warehouse[object[0]][object[1]];
                warehouse[object[0]][object[1]] = ".";
                return object;
            } else {
                int[] moved = movePlus(warehouse, next, instruction);
                if (moved != null) {
                    warehouse[moved[0]][moved[1]] = warehouse[object[0]][object[1]];
                    warehouse[object[0]][object[1]] = ".";
                    return object;
                }
                return null;
            }
        }
        int[] edge = null;
        if ("[".equals(warehouse[object[0]][object[1]])) {
            edge = object;
        }
        if ("]".equals(warehouse[object[0]][object[1]])) {
            edge = new int[]{object[0], object[1] - 1};
        }
        if (edge != null && isAbleToMove(warehouse, edge, instruction)) {
            moveBox(warehouse, edge, instruction);
            return object;
        }
        return null;
    }

    private void moveBox(final String[][] warehouse, final int[] edge, final String instruction) {
        int[] nextEdge = next(edge, instruction);
        if (".".equals(warehouse[nextEdge[0]][nextEdge[1]])
            && ".".equals(warehouse[nextEdge[0]][nextEdge[1] + 1])) {
            warehouse[edge[0]][edge[1]] = ".";
            warehouse[edge[0]][edge[1] + 1] = ".";
            warehouse[nextEdge[0]][nextEdge[1]] = "[";
            warehouse[nextEdge[0]][nextEdge[1] + 1] = "]";
        } else if (".".equals(warehouse[nextEdge[0]][nextEdge[1]])
                && "<".equals(instruction)) {
            warehouse[edge[0]][edge[1]] = ".";
            warehouse[edge[0]][edge[1] + 1] = ".";
            warehouse[nextEdge[0]][nextEdge[1]] = "[";
            warehouse[nextEdge[0]][nextEdge[1] + 1] = "]";
        } else if (".".equals(warehouse[nextEdge[0]][nextEdge[1] + 1])
                && ">".equals(instruction)) {
            warehouse[edge[0]][edge[1]] = ".";
            warehouse[edge[0]][edge[1] + 1] = ".";
            warehouse[nextEdge[0]][nextEdge[1]] = "[";
            warehouse[nextEdge[0]][nextEdge[1] + 1] = "]";
        } else {
            if ("v".equals(instruction) || "^".equals(instruction)) {
                if ("]".equals(warehouse[nextEdge[0]][nextEdge[1]])) {
                    moveBox(warehouse, new int[]{nextEdge[0], nextEdge[1] - 1}, instruction);
                } else if ("[".equals(warehouse[nextEdge[0]][nextEdge[1]])) {
                    moveBox(warehouse, nextEdge, instruction);
                }
                if ("[".equals(warehouse[nextEdge[0]][nextEdge[1] + 1])) {
                    moveBox(warehouse, new int[]{nextEdge[0], nextEdge[1] + 1}, instruction);
                }
                moveBox(warehouse, edge, instruction);
            } else {
                if ("<".equals(instruction)) {
                    moveBox(warehouse, new int[]{nextEdge[0], nextEdge[1] - 1}, instruction);
                }
                if (">".equals(instruction)) {
                    moveBox(warehouse, new int[]{nextEdge[0], nextEdge[1] + 1}, instruction);
                }
                moveBox(warehouse, edge, instruction);
            }
        }
    }

    private boolean isAbleToMove(final String[][] warehouse, final int[] object, final String instruction) {
        if ("#".equals(warehouse[object[0]][object[1]])) {
            return false;
        }
        int[] edge = null;
        if ("[".equals(warehouse[object[0]][object[1]])) {
            edge = object;
        } else  if ("]".equals(warehouse[object[0]][object[1]])) {
            edge = new int[]{object[0], object[1] - 1};
        }
        if (edge != null) {
            int[] nextEdge = next(edge, instruction);
            return (".".equals(warehouse[nextEdge[0]][nextEdge[1]])
                    && ".".equals(warehouse[nextEdge[0]][nextEdge[1] + 1]))
                    || (
                    ".".equals(warehouse[nextEdge[0]][nextEdge[1]])
                            && "[".equals(warehouse[nextEdge[0]][nextEdge[1] + 1])
                            && "<".equals(instruction)
                    )
                    || (
                    "]".equals(warehouse[nextEdge[0]][nextEdge[1]])
                            && ".".equals(warehouse[nextEdge[0]][nextEdge[1] + 1])
                            && ">".equals(instruction)
                    )
                    || (
                        ">".equals(instruction)
                        && isAbleToMove(warehouse, new int[]{nextEdge[0], nextEdge[1] + 1}, instruction)
                    )
                    || (
                        "<".equals(instruction)
                        && isAbleToMove(warehouse, nextEdge, instruction)
                    )
                    || (
                          ("^".equals(instruction) || "v".equals(instruction))
                          && isAbleToMove(warehouse, nextEdge, instruction)
                          && isAbleToMove(warehouse, new int[]{nextEdge[0], nextEdge[1] + 1}, instruction)
                    );
        } else if ("@".equals(warehouse[object[0]][object[1]])) {
            int[] next = next(object, instruction);
            return (".".equals(warehouse[next[0]][next[1]])) || isAbleToMove(warehouse, next, instruction);
        }
        return true;
    }


    private int[] next(final int[] from, final String instruction) {
        return switch (instruction) {
            case "^" -> new int[]{from[0] - 1, from[1]};
            case ">" -> new int[]{from[0], from[1] + 1};
            case "v" -> new int[]{from[0] + 1, from[1]};
            case "<" -> new int[]{from[0], from[1] - 1};
            default -> from;
        };
    }
}
