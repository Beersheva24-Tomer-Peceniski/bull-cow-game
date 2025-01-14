package telran.bullcow.entities;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Gamer {

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
    
}
