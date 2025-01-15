package telran.bullcow.menus;

import java.util.Arrays;

import telran.bullcow.services.BullCowService;
import telran.view.InputOutput;
import telran.view.Item;

public class GameMenu {

    private static BullCowService service;

    public static Item[] getItems(BullCowService service) {
        GameMenu.service = service;
        Item[] items = {
                Item.of("Start a Game", GameMenu::availableGames),
                Item.of("Join a game", GameMenu::getJoinableGames),
                Item.of("Create a new game", GameMenu::createGame),
                Item.ofExit()
        };
        return items;
    }

    static void availableGames(InputOutput io) {
        Long[] games = service.getRunnableGames();
        StringBuilder sb = new StringBuilder().append("These are the available Games that you can start:")
                .append("\n-> Game ")
                .append(String.join("\n-> Game ", Arrays.stream(games).map(l -> String.valueOf(l)).toArray(String[]::new)));
        String gamesString = sb.toString();
        String noGames = "There is no available games, please create a new game";
        String print = games.length == 0 ? noGames : gamesString;
        io.writeLine(print);
    }

    static void getJoinableGames(InputOutput io) {
        Long[] games = service.getJoinableGames();
        StringBuilder sb = new StringBuilder().append("These are the available Games that you can join:")
                .append("\n-> Game ")
                .append(String.join("\n-> Game ", Arrays.stream(games).map(l -> String.valueOf(l)).toArray(String[]::new)));
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
}
