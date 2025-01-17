package telran.bullcow.menus;

import java.util.Arrays;

import telran.bullcow.entities.Game;
import telran.bullcow.entities.Move;
import telran.bullcow.exceptions.GameNotFoundException;
import telran.bullcow.services.BullCowService;
import telran.view.InputOutput;
import telran.view.Item;

public class GameMenu {

    private static BullCowService service;

    public static Item[] getItems(BullCowService service) {
        GameMenu.service = service;

        Item[] items = {
                Item.of("Create a new game", GameMenu::createGame),
                Item.of("Start a Game and make a move", GameMenu::startGame),
                Item.of("Join a game and make a move", GameMenu::joinGame),
                Item.of("Make a move in a started game", GameMenu::makeMove),
                Item.ofExit()
        };
        return items;
    }

    static void printRunnableGames(InputOutput io) {
        Long[] games = service.getRunnableGames();
        StringBuilder sb = new StringBuilder().append("These are the available Games that you can start:")
                .append("\n-> Game ")
                .append(String.join("\n-> Game ",
                        Arrays.stream(games).map(l -> String.valueOf(l)).toArray(String[]::new)));
        String gamesString = sb.toString();
        String noGames = "There is no available games, please create a new game";
        String print = games.length == 0 ? noGames : gamesString;
        io.writeLine(print);
    }

    static void printJoinableGames(InputOutput io) {
        Long[] games = service.getJoinableGames();
        StringBuilder sb = new StringBuilder().append("These are the available Games that you can join:")
                .append("\n-> Game ")
                .append(String.join("\n-> Game ",
                        Arrays.stream(games).map(l -> String.valueOf(l)).toArray(String[]::new)));
        String gamesString = sb.toString();
        String noGames = "There is no games to join, please create a new game";
        String print = games.length == 0 ? noGames : gamesString;
        io.writeLine(print);
    }

    static void printStartedGames(InputOutput io) {
        Long[] games = service.getStartedGames();
        StringBuilder sb = new StringBuilder().append("These are the available Games that you can make a move:")
                .append("\n-> Game ")
                .append(String.join("\n-> Game ",
                        Arrays.stream(games).map(l -> String.valueOf(l)).toArray(String[]::new)));
        String gamesString = sb.toString();
        String noGames = "There is no available games, please create a new game";
        String print = games.length == 0 ? noGames : gamesString;
        io.writeLine(print);
    }

    static void createGame(InputOutput io) {
        Long id = service.createGame();
        io.writeLine("");
        io.writeLine(String.format("You have created the game with id: %d", id));
    }

    static void startGame(InputOutput io) {
        printRunnableGames(io);
        Long[] games = service.getRunnableGames();
        Long gameId = io.readLong("\nPlease insert the game ID of the game you want to start",
                "Please insert a number");
        Game game = service.getGame(gameId);
        if ((game == null) || !(Arrays.asList(games).contains(gameId))) {
            throw new GameNotFoundException(gameId);
        }
        service.startGame(game);
        move(io);
    }

    static void joinGame(InputOutput io) {
        printJoinableGames(io);
        Long[] games = service.getJoinableGames();
        Long gameId = io.readLong("\nPlease insert the game ID of the game you want to join",
                "Please insert a number");
        Game game = service.getGame(gameId);
        if ((game == null) || !(Arrays.asList(games).contains(gameId))) {
            throw new GameNotFoundException(gameId);
        }
        service.joinGame(game);
        service.startGame(game);
        move(io);
    }

    static void makeMove(InputOutput io) {
        printStartedGames(io);
        Long[] games = service.getStartedGames();
        Long gameId = io.readLong("\nPlease insert the game ID of the game you want to make a move",
                "Please insert a number");
        Game game = service.getGame(gameId);
        if ((game == null) || !(Arrays.asList(games).contains(gameId))) {
            throw new GameNotFoundException(gameId);
        }
        service.logGame(game.getId());
        move(io);
    }

    private static void move(InputOutput io) {
        String sequence = io.readString("Please insert your sequence");
        Move move = service.makeMove(sequence);
        if(move.getBulls() == 4) {
            System.out.println("You won the game!!");
        } else {
            System.out.printf("You got %d bulls and %d cows!\n", move.getBulls(), move.getCows());
        }
    }
}
