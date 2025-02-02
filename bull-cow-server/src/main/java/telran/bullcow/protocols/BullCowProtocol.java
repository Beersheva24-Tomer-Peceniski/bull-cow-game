package telran.bullcow.protocols;

import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.json.JSONArray;

import telran.bullcow.entities.Game;
import telran.bullcow.entities.Gamer;
import telran.bullcow.entities.Move;
import telran.bullcow.services.*;

public class BullCowProtocol implements Protocol {

    BullCowService service;

    public BullCowProtocol(BullCowService service) {
        this.service = service;
    }

    @Override
    public Response getResponse(Request request) {
        String requestType = request.requestType();
        String requestData = request.requestData();
        Response response = null;
        try {
            Method method = BullCowProtocol.class.getDeclaredMethod(requestType, String.class);
            method.setAccessible(true);
            response = (Response) method.invoke(this, requestData);
        } catch (NoSuchMethodException e) {
            response = new Response(ResponseCode.WRONG_TYPE, requestType + " Wrong type");
        } catch (InvocationTargetException e) {
            Throwable causeExc = e.getCause();
            String message = causeExc == null ? e.getMessage() : causeExc.getMessage();
            response = new Response(ResponseCode.WRONG_DATA, message);
        } catch (Exception e) {
            // only for finishing Server and printing out Exception full stack
            throw new RuntimeException(e);
        }
        return response;
    }

    public Response getOkResponse(String responseData) {
        return new Response(ResponseCode.OK, responseData);
    }

    public Response logIn(String requestedData) {
        service.logIn(requestedData);
        return getOkResponse("");
    }

    public Response register(String requestedData) {
        Gamer gamer = Gamer.getGamerFromJSON(requestedData);
        System.out.println("passed here");
        System.out.println(gamer);
        System.out.println("passed also here");
        service.register(gamer);
        return getOkResponse("");
    }

    public Response getRunnableGames(String requestedData) {
        Long [] games = service.getRunnableGames();
        JSONArray jsonArray = new JSONArray(games);
        return getOkResponse(jsonArray.toString());
    }

    public Response getStartedGames(String requestedData) {
        Long [] games = service.getStartedGames();
        JSONArray jsonArray = new JSONArray(games);
        return getOkResponse(jsonArray.toString());
    }

    public Response getJoinableGames(String requestedData) {
        Long[] games = service.getJoinableGames();
        JSONArray jsonArray = new JSONArray(games);
        return getOkResponse(jsonArray.toString());
    }

    public Response createGame(String requestedData) {
        Long id = service.createGame();
        return getOkResponse(String.valueOf(id));
    }

    public Response logOut(String requestedData) {
        service.logOut();
        return getOkResponse("");
    }

    public Response getGamer(String requestedData) {
        Gamer gamer = service.getGamer(requestedData);
        String res = gamer == null ? "" : gamer.toString();
        return getOkResponse(res);
    }

    public Response getGame(String requestedData) {
        Game game = service.getGame(Long.parseLong(requestedData));
        String res = game == null ? "" : game.toString();
        return getOkResponse(res);
    }

    public Response startGame(String requestedData) {
        Game game = Game.getGameFromJSON(requestedData);
        service.startGame(game);
        return getOkResponse("");
    }

    public Response makeMove(String requestedData) {
        Move move = service.makeMove(requestedData);
        return getOkResponse(move.toString());
    }

    public Response logGame(String requestedData) {
        service.logGame(Long.parseLong(requestedData));
        return getOkResponse("");
    }

    public Response joinGame(String requestedData) {
        service.joinGame(Game.getGameFromJSON(requestedData));
        return getOkResponse("");
    }

}
