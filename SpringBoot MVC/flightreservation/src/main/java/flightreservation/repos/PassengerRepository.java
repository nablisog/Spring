package flightreservation.repos;

import flightreservation.entities.Passenger;
import flightreservation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {
}
