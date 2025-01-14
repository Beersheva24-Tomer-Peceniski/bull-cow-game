package telran.bullcow.services;

import java.util.List;

import telran.bullcow.entities.Gamer;

public interface BullCowService {

    void logIn(String username);

    void register(Gamer gamer);

    List<Long> getRunnableGames(String username);

}
