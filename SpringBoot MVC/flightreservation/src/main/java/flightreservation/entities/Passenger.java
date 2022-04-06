package flightreservation.entities;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Passenger extends AbstractEntity {
    private String firstName;
    private String lastName;
    private String middelName;
    private String email;
    private String phone;
}
