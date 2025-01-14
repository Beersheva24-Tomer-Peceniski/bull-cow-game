package telran.bullcow.exceptions;

import java.util.NoSuchElementException;

public class GamerNotFoundException extends NoSuchElementException {

    public GamerNotFoundException(String username) {
        super(String.format("Gamer with username: %s was not found", username));
    }

}
