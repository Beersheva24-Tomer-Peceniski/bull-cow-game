package telran.bullcow.menus;

import java.util.Arrays;

import telran.bullcow.entities.Game;
import telran.bullcow.exceptions.GameNotFoundException;
import telran.bullcow.services.BullCowService;
import telran.view.InputOutput;
import telran.view.Item;

public class GameMenu {

    private static BullCowService service;

    public static Item[] getItems(BullCowService service) {
        GameMenu.service = service;

        Item[] items = {
                Item.of("Start a Game", GameMenu::startGame),
                Item.of("Join a game", GameMenu::getJoinableGames),
                Item.of("Create a new game", GameMenu::createGame),
                Item.ofExit()
        };
        return items;
    }

    static void printAvailableGames(InputOutput io) {
        Long[] games = service.getRunnableGames();
        StringBuilder sb = new StringBuilder().append("These are the available Games that you can start:")
                .append("\n-> Game ")
                .append(String.join("\n-> Game ",
                        Arrays.stream(games).map(l -> String.valueOf(l)).toArray(String[]::new)));
        String gamesString = sb.toString();
        String noGames = "There is no available games, please create a new game";
        String print = games.length == 0 ? noGames : gamesString;
        io.writeLine(print);
    }

    static void getJoinableGames(InputOutput io) {
        Long[] games = service.getJoinableGames();
        StringBuilder sb = new StringBuilder().append("These are the available Games that you can join:")
                .append("\n-> Game ")
                .append(String.join("\n-> Game ",
                        Arrays.stream(games).map(l -> String.valueOf(l)).toArray(String[]::new)));
        String gamesString = sb.toString();
        String noGames = "There is no games to join, please create a new game";
        String print = games.length == 0 ? noGames : gamesString;
        io.writeLine(print);
    }

    static void createGame(InputOutput io) {
        Long id = service.createGame();
        io.writeLine("");
        io.writeLine(String.format("You have created the game with id: %d", id));
    }

    static void startGame(InputOutput io) {
        printAvailableGames(io);
        Long[] games = service.getRunnableGames();
        Long gameId = io.readLong("\nPlease insert the game ID of the game you want to start",
                "Please insert a number");
        Game game = service.getGame(gameId);
        if ((game == null) || !(Arrays.asList(games).contains(gameId))) {
            throw new GameNotFoundException(gameId);
        }
        service.startGame(game);
        makeMove(io);
    }

    private static void makeMove(InputOutput io) {
        String sequence = io.readString("Please insert your sequence");
        service.makeMove(sequence);
    }
}
