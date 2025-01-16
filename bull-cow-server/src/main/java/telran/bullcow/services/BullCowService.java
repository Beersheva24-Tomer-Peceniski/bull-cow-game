package telran.bullcow.services;

import telran.bullcow.entities.Game;
import telran.bullcow.entities.Gamer;
import telran.bullcow.entities.Move;

public interface BullCowService {

    void logIn(String username);

    void register(Gamer gamer);

    Long[] getRunnableGames();

    Long[] getJoinableGames();

    Long createGame();

    void logOut();

    Gamer getGamer(String username);

    Game getGame(Long id);

    void startGame(Game game);

    Move makeMove(String sequence);

}
