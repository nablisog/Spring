package com.booking.hotel.repository;

import com.booking.hotel.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation,String> {
    Reservation findReservationById(String id);

}
