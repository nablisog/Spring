package flightreservation.repos;

import flightreservation.entities.Reservation;
import flightreservation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
