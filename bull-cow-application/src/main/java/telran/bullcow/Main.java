package telran.bullcow;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;

import telran.bullcow.menu.MenuItems;
import telran.bullcow.services.BullCowProxy;
import telran.bullcow.services.BullCowService;
import telran.net.NetworkClient;
import telran.net.TcpClient;
import telran.view.*;

public class Main {
    private static final String HOST = "localhost";
    private static final int PORT = 4000;

    public static void main(String[] args) {
        InputOutput io = new StandardInputOutput();
        NetworkClient netClient = new TcpClient(HOST, PORT);
        BullCowService service = new BullCowProxy(netClient);
        Item[] items = MenuItems.getItems(service);
        items = addExitItem(items, netClient);
        Menu menu = new Menu("Bull Cow Game", items);
        menu.perform(io);
        io.writeLine("Session Finished");
    }

    private static Item[] addExitItem(Item[] items, NetworkClient netClient) {
        Item[] res = Arrays.copyOf(items, items.length + 1);
        res[items.length] = Item.of("Exit", io -> {
            try {
                if (netClient instanceof Closeable closeable)
                    closeable.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, true);
        return res;
    }
}