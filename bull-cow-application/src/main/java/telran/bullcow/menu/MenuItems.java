package telran.bullcow.menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import telran.bullcow.entities.Gamer;
import telran.bullcow.services.BullCowService;
import telran.view.InputOutput;
import telran.view.Item;

public class MenuItems {

    private static BullCowService service;

    public static Item[] getItems(BullCowService service) {
        MenuItems.service = service;
        Item[] items = {
            Item.of("Log in", MenuItems::logIn),
            Item.of("Register", MenuItems::register),
            Item.of("Start a Game", MenuItems::availableGames),
            Item.of("Join a game", MenuItems::getJoinableGames)
        };
        return items;
    }

    static void logIn(InputOutput io) {
        String username = io.readString("Insert the username");
        service.logIn(username);
    }

    static void register(InputOutput io) {
        String username = io.readString("Insert the username to be created");
        String birthdateString = io.readString("Insert the birthdate in the pattern yyyy-MM-dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdate = LocalDate.parse(birthdateString, dtf);
        service.register(new Gamer(username, birthdate));
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
}
