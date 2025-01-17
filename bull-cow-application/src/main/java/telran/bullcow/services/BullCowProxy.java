package telran.bullcow.services;

import java.util.List;

import org.json.JSONArray;

import telran.bullcow.entities.Game;
import telran.bullcow.entities.Gamer;
import telran.bullcow.entities.Move;
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

    @Override
    public Long[] getStartedGames() {
        String responseData = netClient.sendAndReceive("getStartedGames", "");
        List<Object> list = new JSONArray(responseData).toList();
        return list.stream().map(o -> ((Number) o).longValue()).toArray(Long[]::new);
    }

    @Override
    public Long[] getJoinableGames() {
        String responseData = netClient.sendAndReceive("getJoinableGames", "");
        List<Object> list = new JSONArray(responseData).toList();
        return list.stream().map(o -> ((Number) o).longValue()).toArray(Long[]::new);
    }

    public Long createGame() {
        String responseData = netClient.sendAndReceive("createGame", "");
        return Long.parseLong(responseData);
    }

    public void logOut() {
        netClient.sendAndReceive("logOut", "");
    }

    public Gamer getGamer(String username) {
        String responseData = netClient.sendAndReceive("getGamer", username);
        Gamer gamer = responseData.isEmpty() ? null : Gamer.getGamerFromJSON(responseData);
        return gamer;
    }

    public Game getGame(Long id) {
        String responseData = netClient.sendAndReceive("getGame", String.valueOf(id));
        Game game = responseData.isEmpty() ? null : Game.getGameFromJSON(responseData);
        return game;
    }

    @Override
    public void startGame(Game game) {
        netClient.sendAndReceive("startGame", game.toString());
    }

    @Override
    public Move makeMove(String sequence) {
        String moveString = netClient.sendAndReceive("makeMove", sequence);
        return Move.getMoveFromJSON(moveString);
    }

    @Override 
    public void logGame(Long id) {
        netClient.sendAndReceive("logGame", String.valueOf(id));
    }

    @Override
    public void joinGame(Game game) {
        netClient.sendAndReceive("joinGame", game.toString());
    }

}
