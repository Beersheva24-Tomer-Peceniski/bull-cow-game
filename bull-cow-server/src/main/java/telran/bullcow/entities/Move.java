package telran.bullcow.entities;

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

    public Move() {}

    public Move(GameGamer gameGamer, String sequence) {
        this.gameGamer = gameGamer;
        this.sequence = sequence;
        setBullsAndCows();
        checkIfWinner();
    }

    private void setBullsAndCows() {
        String gameSequence = gameGamer.getGame().getSequence();
        for(int i = 0; i < 4; i++) {
            if(gameSequence.charAt(i) == sequence.charAt(i)) {
                bulls++;
            } else if (gameSequence.contains(String.valueOf(sequence.charAt(i)))) {
                cows++;
            }
        }
    }

    private void checkIfWinner() {
        if(bulls == 4) {
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

    
}
