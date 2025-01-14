package telran.bullcow.menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
            Item.of("Register", MenuItems::register)
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
}
