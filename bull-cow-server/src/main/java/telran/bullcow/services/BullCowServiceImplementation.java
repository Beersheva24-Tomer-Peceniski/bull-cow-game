package telran.bullcow.services;

import telran.bullcow.entities.Game;
import telran.bullcow.entities.GameGamer;
import telran.bullcow.entities.Gamer;
import telran.bullcow.entities.Move;
import telran.bullcow.exceptions.GameNotFoundException;
import telran.bullcow.exceptions.GamerNotFoundException;
import telran.bullcow.exceptions.NoLoginException;
import telran.bullcow.repositories.BullCowRepository;

public class BullCowServiceImplementation implements BullCowService {

    BullCowRepository repository;
    Gamer loggedGamer;
    Game loggedGame;

    public BullCowServiceImplementation(BullCowRepository repository) {
        this.repository = repository;
    }

    @Override
    public void logIn(String username) {
        Gamer existingGamer = repository.getGamer(username);
        if(existingGamer == null) throw new GamerNotFoundException(username);
        loggedGamer = existingGamer;
    }

    @Override
    public void register(Gamer gamer) {
        repository.createGamer(gamer);
    }

    @Override
    public Long[] getRunnableGames() {
        if(loggedGamer == null) throw new NoLoginException();
        return repository.getStartableGames(loggedGamer.getUsername()).toArray(Long[]::new);
    }

    @Override
    public Long[] getStartedGames() {
        if(loggedGamer == null) throw new NoLoginException();
        return repository.getStartedGames(loggedGamer.getUsername()).toArray(Long[]::new);
    }

    @Override
    public Long[] getJoinableGames() {
        if(loggedGamer == null) throw new NoLoginException();
        return repository.getJoinableGames(loggedGamer.getUsername()).toArray(Long[]::new);
    }

    @Override
    public Long createGame() {
        if(loggedGamer == null) throw new NoLoginException();
        Game game = new Game();
        GameGamer gameGamer = new GameGamer(game, loggedGamer);
        return repository.createGame(game, gameGamer);
    }

    @Override
    public void logOut() {
        loggedGamer = null;
    }

    @Override
    public Gamer getGamer(String username) {
        return repository.getGamer(username);
    }

    @Override
    public Game getGame(Long id) {
        return repository.getGame(id);
    }

    @Override
    public void startGame(Game game) {
        loggedGame = game;
        repository.setGameStartDate(game.getId());
    }

    @Override
    public Move makeMove(String sequence) {
        return repository.makeMove(loggedGamer.getUsername(), loggedGame.getId(), sequence);
    }

    @Override 
    public void logGame(Long id) {
        Game existingGame = repository.getGame(id);
        if(existingGame == null) throw new GameNotFoundException(id);
        loggedGame = existingGame;
    }
}
