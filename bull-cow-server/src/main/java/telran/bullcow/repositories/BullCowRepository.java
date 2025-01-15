package telran.bullcow.repositories;

import java.util.List;

import telran.bullcow.entities.Game;
import telran.bullcow.entities.GameGamer;
import telran.bullcow.entities.Gamer;

public interface BullCowRepository {

    Long createGame(Game game, GameGamer gameGamer);

    Game getGame(Long id);

    List<Long> getJoinableGames(String username);

    List<Long> getStartableGames(String username);

    void setGameStartDate(Long gameId);

    void setFinishGame(Long gameId);

    Gamer getGamer(String username);

    void createGamer(Gamer gamer);

    void joinGame(String gamerUsername, Long gameId);

    GameGamer getGameGamer(String gamerUsername, Long gameId);

    Long makeMove(String gamerUsername, Long gameId, String sequence);

}
