package telran.bullcow.exceptions;

import java.util.NoSuchElementException;

public class GameNotFoundException extends NoSuchElementException {

    public GameNotFoundException(long id) {
        super(String.format("Game with id: %d not found", id));
    }

}
