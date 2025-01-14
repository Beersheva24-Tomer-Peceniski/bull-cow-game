package telran.bullcow.entities;

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

    public GameGamer() {}
    
    public GameGamer(Game game, Gamer gamer) {
        this.game = game;
        this.gamer = gamer;
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
    
}
