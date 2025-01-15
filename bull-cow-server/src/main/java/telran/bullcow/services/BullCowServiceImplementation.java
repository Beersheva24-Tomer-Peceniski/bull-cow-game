package telran.bullcow.services;

import telran.bullcow.entities.Gamer;
import telran.bullcow.exceptions.GamerNotFoundException;
import telran.bullcow.repositories.BullCowRepository;

public class BullCowServiceImplementation implements BullCowService {

    BullCowRepository repository;
    Gamer loggedGamer;

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
        if(loggedGamer == null) throw new RuntimeException("There was no log in");
        return repository.getStartableGames(loggedGamer.getUsername()).toArray(Long[]::new);
    }

    @Override
    public Long[] getJoinableGames() {
        if(loggedGamer == null) throw new RuntimeException("There was no log in");
        return repository.getJoinableGames(loggedGamer.getUsername()).toArray(Long[]::new);
    }

}
