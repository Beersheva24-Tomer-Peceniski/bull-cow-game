package telran.bullcow.exceptions;

import java.util.NoSuchElementException;

public class GameGamerNotFoundException extends NoSuchElementException {

    public GameGamerNotFoundException() {
        super(String.format("GameGamer not found"));
    }

}
