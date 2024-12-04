package matheus.software.aoc2024.day04;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public final class WordPuzzle {
    public long findXMAS(final String raw) {
        char[][] puzzle = parsePuzzle(raw);
        long count = 0;
        for (int i = 0; i < puzzle.length; i += 1) {
            for (int j = 0; j < puzzle[i].length; j += 1) {
                count += countXMAS(puzzle, i, j);
            }
        }
        return count;
    }

    public long find2MAS(final String raw) {
        char[][] puzzle = parsePuzzle(raw);
        long count = 0;
        for (int i = 1; i < puzzle.length - 1; i += 1) {
            for (int j = 1; j < puzzle[i].length - 1; j += 1) {
                count += isXMAS(puzzle, i, j) ? 1 : 0;
            }
        }
        return count;
    }

    private char[][] parsePuzzle(final String raw) {
        String[] lines = raw.split("\\n");
        return Arrays.stream(lines)
                .map(String::toCharArray)
                .toList()
                .toArray(new char[][]{});

    }

    private boolean isXMAS(final char[][] puzzle, final int i, final int j) {
        return
                ((puzzle[i - 1][j - 1] == 'M'
                    && puzzle[i][j] == 'A'
                    && puzzle[i + 1][j + 1] == 'S')
                        ||
                 (puzzle[i - 1][j - 1] == 'S'
                    && puzzle[i][j] == 'A'
                    && puzzle[i + 1][j + 1] == 'M'))
                        &&
                ((puzzle[i - 1][j + 1] == 'M'
                    && puzzle[i][j] == 'A'
                    && puzzle[i + 1][j - 1] == 'S')
                        ||
                 (puzzle[i - 1][j + 1] == 'S'
                    && puzzle[i][j] == 'A'
                    && puzzle[i + 1][j - 1] == 'M'));
    }


    private int countXMAS(final char[][] puzzle, final int i, final int j) {
        int count = 0;
        if (i >= 3) {
            count += isFullXMAS(
                new char[]{puzzle[i][j], puzzle[i - 1][j], puzzle[i - 2][j], puzzle[i - 3][j]}
            ) ? 1 : 0;
            if (j >= 3) {
                count += isFullXMAS(
                    new char[]{puzzle[i][j], puzzle[i - 1][j - 1], puzzle[i - 2][j - 2], puzzle[i - 3][j - 3]}
                ) ? 1 : 0;
            }
        }

        if (j >= 3) {
            count += isFullXMAS(
                new char[]{puzzle[i][j], puzzle[i][j - 1], puzzle[i][j - 2], puzzle[i][j - 3]}
            ) ? 1 : 0;
            if (i < (puzzle.length - 3)) {
                count += isFullXMAS(
                    new char[]{puzzle[i][j], puzzle[i + 1][j - 1], puzzle[i + 2][j - 2], puzzle[i + 3][j - 3]}
                ) ? 1 : 0;
            }
        }

        if (i < (puzzle.length - 3)) {
            count += isFullXMAS(
                new char[]{puzzle[i][j], puzzle[i + 1][j], puzzle[i + 2][j], puzzle[i + 3][j]}
            ) ? 1 : 0;
            if (j < (puzzle[i].length - 3)) {
                count += isFullXMAS(
                    new char[]{puzzle[i][j], puzzle[i + 1][j + 1], puzzle[i + 2][j + 2], puzzle[i + 3][j + 3]}
                ) ? 1 : 0;
            }
        }

        if (j < (puzzle[i].length - 3)) {
            count += isFullXMAS(
                new char[]{puzzle[i][j], puzzle[i][j + 1], puzzle[i][j + 2], puzzle[i][j + 3]}
            ) ? 1 : 0;
            if (i >= 3) {
                count += isFullXMAS(
                    new char[]{puzzle[i][j], puzzle[i - 1][j + 1], puzzle[i - 2][j + 2], puzzle[i - 3][j + 3]}
                ) ? 1 : 0;
            }
        }
        return count;
    }

    private boolean isFullXMAS(final char[] word) {
        return String.valueOf(word).equals("XMAS");
    }
}
