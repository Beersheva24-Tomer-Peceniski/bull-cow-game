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
    public List<Long> getRunnableGames(String username) {
        String responseData = netClient.sendAndReceive("getRunnableGames", username);
        List<Long> res = new JSONArray(responseData).toList().stream().map(o -> Long.parseLong(o.toString())).toList();
        return res;
    }

}
