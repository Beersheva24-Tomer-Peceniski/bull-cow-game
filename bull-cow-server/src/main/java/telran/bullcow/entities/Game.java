package telran.bullcow.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;

import org.json.JSONObject;

import jakarta.persistence.*;

@Entity
public class Game {

    private static DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime startDate;
    Boolean isFinished = false;
    String sequence;

    public Game() {
        setSequence();
    }

    private Game(Long id, LocalDateTime startDate, Boolean isFinished, String sequence) {
        this.id = id;
        this.startDate = startDate;
        this.isFinished = isFinished;
        this.sequence = sequence;
    }

    private Game(Long id, Boolean isFinished, String sequence) {
        this.id = id;
        this.isFinished = isFinished;
        this.sequence = sequence;
    }

    public void setSequence() {
        HashSet<String> sequenceSet = new HashSet<>();
        Random random = new Random();
        int i = 0;
        while (i < 4) {
            int number = random.nextInt(10);
            String numberString = String.valueOf(number);
            if (sequenceSet.add(numberString)) {
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

    static public Game getGameFromJSON(String jsonString) {
        Game game;
        JSONObject json = new JSONObject(jsonString);
        Long id = json.getLong("id");
        Boolean isFinished = json.getBoolean("isFinished");
        String sequece = json.getString("sequence");
        if(json.has("startDate")) {
            String startDateString = json.getString("startDate");
            LocalDateTime startDate = startDateString == null ? null : LocalDateTime.parse(startDateString, dtf);
            game = new Game(id, startDate, isFinished, sequece);
        }
        game = new Game(id, isFinished, sequece);
        return game;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        if(startDate != null) json.put("startDate", startDate.format(dtf));
        json.put("isFinished", isFinished);
        json.put("sequence", sequence);
        return json.toString();
    }

}
