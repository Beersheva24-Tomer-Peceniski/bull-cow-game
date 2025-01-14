package telran.bullcow.menu;

import telran.bullcow.services.BullCowService;
import telran.view.InputOutput;
import telran.view.Item;

public class MenuItems {

    private static BullCowService service;

    public static Item[] getItems(BullCowService service) {
        MenuItems.service = service;
        Item[] items = {
            Item.of("Log in", MenuItems::logIn)
        };
        return items;
    }

    static void logIn(InputOutput io) {
        String username = io.readString("Insert the username");
        service.logIn(username);
    }
}
