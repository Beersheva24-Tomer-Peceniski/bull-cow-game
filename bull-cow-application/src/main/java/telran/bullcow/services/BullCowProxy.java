package telran.bullcow.services;

import java.util.List;

import org.json.JSONArray;

import telran.bullcow.entities.Gamer;
import telran.net.NetworkClient;

public class BullCowProxy implements BullCowService{

    private NetworkClient netClient;

    public BullCowProxy(NetworkClient netClient) {
        this.netClient = netClient;
    }

    @Override
    public void logIn(String username) {
        netClient.sendAndReceive("logIn", username);
    }

    @Override
    public void register(Gamer gamer) {
        netClient.sendAndReceive("register", gamer.toString());
    }

    @Override
    public Long[] getRunnableGames() {
        String responseData = netClient.sendAndReceive("getRunnableGames", "");
        List<Object> list = new JSONArray(responseData).toList();
        return list.stream().map(o -> ((Number) o).longValue()).toArray(Long[]::new);
    }

}
