package telran.bullcow.exceptions;

import telran.bullcow.entities.GameGamer;

public class GameGamerAlreadyExistsException extends RuntimeException {

    public GameGamerAlreadyExistsException(GameGamer gameGamer) {
        super(String.format("GameGamer %d already exists", gameGamer.getId()));
    }
}
