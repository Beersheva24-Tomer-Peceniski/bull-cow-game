package telran.bullcow.menus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import telran.bullcow.entities.Gamer;
import telran.bullcow.exceptions.GamerAlreadyExistsException;
import telran.bullcow.services.BullCowService;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class LoginMenu {

    private static BullCowService service;
    private static InputOutput io;

    public static Item[] getItems(BullCowService service, InputOutput io) {
        LoginMenu.service = service;
        LoginMenu.io = io;
        Item[] items = {
                Item.of("Register and Log In", LoginMenu::register),
                Item.of("Log in", LoginMenu::logIn),
                Item.ofExit()
        };
        return items;
    }

    static void register(InputOutput io) {
        String username = io.readString("Insert the username to be created");
        Gamer existingGamer = service.getGamer(username);
        if(existingGamer != null) throw new GamerAlreadyExistsException(username);
        String birthdateString = io.readString("Insert the birthdate in the pattern yyyy-MM-dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdate = LocalDate.parse(birthdateString, dtf);
        Gamer gamer = new Gamer(username, birthdate);
        service.register(gamer);
        service.logIn(gamer.getUsername());
        gameMenu(gamer.getUsername());
    }

    static void logIn(InputOutput io) {
        String username = io.readString("Insert the username");
        service.logIn(username);
        gameMenu(username);
    }

    static void gameMenu(String username) {
        String menuName = String.format("Let's play Bulls and Cows, %s", username);
        Menu menu = new Menu(menuName, GameMenu.getItems(service));
        menu.perform(io);
        service.logOut();
    }
}
