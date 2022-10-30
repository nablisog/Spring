package com.booking.hotel.controller;

import com.booking.hotel.model.Reservation;
import com.booking.hotel.model.Room;
import com.booking.hotel.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/listOfAllReservations")
    public List<Reservation> getAllReservations(){
        return reservationService.allReservations();
    }

    @DeleteMapping("/cancelReservation/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable("id")String id){
        reservationService.cancelReservation(id);
        return ResponseEntity.ok("Reservation has been cancelled");
    }

    @PostMapping("/makeReservation")
    public ResponseEntity<Reservation> makeReservation(@RequestParam(value = "email") String email,
                                                       @RequestParam(value = "roomNr") String roomNr,
                                                       @RequestParam(value = "from")@DateTimeFormat(pattern= "yyyy-MM-dd")LocalDate from,
                                                       @RequestParam(value = "to")@DateTimeFormat(pattern= "yyyy-MM-dd") LocalDate to) throws Exception {

        Reservation reservation = reservationService.bookRoom(email,roomNr,from,to);
        return new ResponseEntity<>(reservation,HttpStatus.CREATED);

    }


    @GetMapping("/fetchReservationByFullName")
    public ResponseEntity<Reservation> getReservationByName(@RequestParam("firstName")String firstName,
                                                            @RequestParam("lastName")String lastName) throws Exception {
        Reservation reservation = reservationService.findReservationByName(firstName,lastName);
        return new ResponseEntity<>(reservation,HttpStatus.OK);
    }

    @GetMapping("/fetchReservationByEmail/{email}")
    public ResponseEntity<Reservation> getReservationByEmail(@PathVariable("email")String email) throws Exception {
        Reservation reservation = reservationService.findReservationByEmail(email);
        return new ResponseEntity<>(reservation,HttpStatus.OK);
    }

    @GetMapping("/ListOfAvailableRooms")
    public List<Room> getAllAvailableRooms(){
        return reservationService.getAvailableRooms();
    }

    




}
