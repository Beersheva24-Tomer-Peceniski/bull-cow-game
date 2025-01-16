package telran.bullcow.entities;

import org.json.JSONObject;

import jakarta.persistence.*;

@Entity
public class GameGamer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    Game game;

    @ManyToOne
    @JoinColumn(name = "gamer_username", nullable = false)
    Gamer gamer;
    Boolean isWinner = false;

    public GameGamer() {
    }

    public GameGamer(Game game, Gamer gamer) {
        this.game = game;
        this.gamer = gamer;
    }

    private GameGamer(Long id, Game game, Gamer gamer, Boolean isWinner) {
        this.id = id;
        this.game = game;
        this.gamer = gamer;
        this.isWinner = isWinner;
    }

    public long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Gamer getGamer() {
        return gamer;
    }

    public Boolean getIsWinner() {
        return isWinner;
    }

    public void setWinner() {
        isWinner = true;
    }

    static public GameGamer getGameGamerFromJSON(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        Long id = json.getLong("id");
        Game game = Game.getGameFromJSON(json.getString("game"));
        Gamer gamer = Gamer.getGamerFromJSON(json.getString("gamer"));
        Boolean isWinner = json.getBoolean("isWinner");
        return new GameGamer(id, game, gamer, isWinner);
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("game", game.toString());
        json.put("gamer", gamer.toString());
        json.put("isWinner", isWinner);
        return json.toString();
    }

}
