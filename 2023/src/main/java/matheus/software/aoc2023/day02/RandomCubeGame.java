package matheus.software.aoc2023.day02;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class RandomCubeGame {

    private List<Game> parseGames(String raw) {
        Stream<String> lines = Arrays.stream(raw.split("\\n"));
        return lines
                .map(a -> a.replaceAll("Game ", ""))
                .map(a -> a.split(":"))
                .map(a -> {
                    int id = Integer.parseInt(a[0]);
                    Game game = new Game(id);
                    for(String round: a[1].split(";")) {
                        game.add(round.split(","));
                    }
                    return game;
                })
                .toList();
    }

    public int possibleGameSum(String raw, int reds, int greens, int blues) {
        return parseGames(raw).stream()
                .filter(a -> a.isPossible(reds, greens, blues))
                .map(Game::getId)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public long gamePowerSum(String raw) {
        return parseGames(raw).stream()
                .map(Game::getPower)
                .reduce(Long::sum)
                .orElse(0L);
    }
}
