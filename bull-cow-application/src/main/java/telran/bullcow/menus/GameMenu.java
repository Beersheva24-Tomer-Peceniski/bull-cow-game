package telran.bullcow.menus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import telran.bullcow.entities.Gamer;
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
        String gamesString = String.join("\n", Arrays.stream(games).map(l -> String.valueOf(l)).toArray(String[]::new));
        String print = games.length == 0 ? "there is no available games" : gamesString;
        io.writeLine(print);
    }

    static void getJoinableGames(InputOutput io) {
        Long[] games = service.getJoinableGames();
        String gamesString = String.join("\n", Arrays.stream(games).map(l -> String.valueOf(l)).toArray(String[]::new));
        String print = games.length == 0 ? "there is no games to join" : gamesString;
        io.writeLine(print);
    }

    static void createGame(InputOutput io) {
        Long id = service.createGame();
        io.writeLine("");
        io.writeLine(String.format("You have created the game with id: %d", id));
    } 
}
