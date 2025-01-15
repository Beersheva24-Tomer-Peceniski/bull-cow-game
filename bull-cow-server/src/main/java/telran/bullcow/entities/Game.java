package telran.bullcow.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;

import jakarta.persistence.*;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime startDate;
    Boolean isFinished = false;
    String sequence;


    public Game() {
        setSequence();
    }

    public void setSequence() {
        HashSet<String> sequenceSet = new HashSet<>();
        Random random = new Random();
        int i = 0;
        while(i < 4) {
            int number = random.nextInt(10);
            String numberString = String.valueOf(number);
            if(sequenceSet.add(numberString)) {
                i++;
            }
        }
        sequence = String.join("", sequenceSet);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public String getSequence() {
        return sequence;
    }

    public void finishGame() {
        isFinished = true;
    }
    
}
