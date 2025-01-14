package telran.bullcow.exceptions;

public class GamerAlreadyExistsException extends RuntimeException {

    public GamerAlreadyExistsException(String username) {
        super(String.format("Gamer %s already exists", username));
    }

}
