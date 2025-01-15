package telran.bullcow.services;

import telran.bullcow.entities.Gamer;

public interface BullCowService {

    void logIn(String username);

    void register(Gamer gamer);

    Long[] getRunnableGames();

    Long[] getJoinableGames();

    Long createGame();

}
