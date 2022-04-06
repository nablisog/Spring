package flightreservation.entities;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Reservation extends AbstractEntity{
    private boolean checkedIn;
    private int numberOfBags;

    @OneToOne
    private Passenger passenger;
    @OneToOne
    private Flight flight;
}
