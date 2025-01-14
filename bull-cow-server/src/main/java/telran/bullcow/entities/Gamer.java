package telran.bullcow.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import jakarta.persistence.*;

@Entity
public class Gamer {

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Id
    String username;
    LocalDate birthdate;

    public Gamer() {}
    
    public Gamer(String username, LocalDate birthdate) {
        this.username = username;
        this.birthdate = birthdate;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    static public Gamer getGamerFromJSON(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        String username = json.getString("username");
        String birthdateString = json.getString("birthdate");
        LocalDate birthdate = LocalDate.parse(birthdateString, dtf);
        return new Gamer(username, birthdate);
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("birthdate", birthdate.format(dtf));
        return json.toString();
    }
    
}
