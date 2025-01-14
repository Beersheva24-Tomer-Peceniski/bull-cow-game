package telran.bullcow.services;

import java.util.List;

import telran.bullcow.entities.Gamer;
import telran.bullcow.exceptions.GamerAlreadyExistsException;
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
    public List<Long> getRunnableGames(String username) {
        return repository.getStartableGames(username);
    }

}
