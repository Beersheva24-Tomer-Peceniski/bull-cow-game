package telran.bullcow.entities;

import org.json.JSONObject;

import jakarta.persistence.*;

@Entity
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "game_gamer_id", nullable = false)
    GameGamer gameGamer;
    String sequence;
    int bulls = 0;
    int cows = 0;

    public Move() {
    }

    public Move(GameGamer gameGamer, String sequence) {
        this.gameGamer = gameGamer;
        this.sequence = sequence;
        setBullsAndCows();
        checkIfWinner();
    }

    private Move(Long id, GameGamer gameGamer, String sequence, int bulls, int cows) {
        this.id = id;
        this.gameGamer = gameGamer;
        this.sequence = sequence;
        this.bulls = bulls;
        this.cows = cows;
    }

    private void setBullsAndCows() {
        String gameSequence = gameGamer.getGame().getSequence();
        for (int i = 0; i < 4; i++) {
            if (gameSequence.charAt(i) == sequence.charAt(i)) {
                bulls++;
            } else if (gameSequence.contains(String.valueOf(sequence.charAt(i)))) {
                cows++;
            }
        }
    }

    private void checkIfWinner() {
        if (bulls == 4) {
            gameGamer.setWinner();
            gameGamer.getGame().finishGame();
        }
    }

    public Long getId() {
        return id;
    }

    public GameGamer getGameGamer() {
        return gameGamer;
    }

    public String getSequence() {
        return sequence;
    }

    public int getBulls() {
        return bulls;
    }

    public int getCows() {
        return cows;
    }

    static public Move getMoveFromJSON(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        Long id = json.getLong("id");
        GameGamer gameGamer = GameGamer.getGameGamerFromJSON(json.getString("gameGamer"));
        String sequence = json.getString("sequence");
        int bulls = json.getInt("bulls");
        int cows = json.getInt("cows");
        return new Move(id, gameGamer, sequence, bulls, cows);
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("gameGamer", gameGamer.toString());
        json.put("sequence", sequence);
        json.put("bulls", bulls);
        json.put("cows", cows);
        return json.toString();
    }

}
