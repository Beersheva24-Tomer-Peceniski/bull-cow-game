package telran.bullcow.exceptions;

public class NoLoginException extends RuntimeException {
    public NoLoginException() {
        super("There is no login done");
    }
}
