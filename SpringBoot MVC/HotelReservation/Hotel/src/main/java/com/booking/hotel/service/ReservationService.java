package com.booking.hotel.service;

import com.booking.hotel.model.Reservation;
import com.booking.hotel.model.Room;
import com.booking.hotel.model.User;
import com.booking.hotel.repository.ReservationRepository;
import com.booking.hotel.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final UserService userService;
    private final EmailService emailService;
    private final RoomRepository roomRepository;



    public List<Reservation> allReservations() {
        return reservationRepository.findAll();
    }

    public void cancelReservation(String id) {
        Reservation reservation = reservationRepository.findReservationById(id);
        reservationRepository.delete(reservation);
    }

    public Reservation bookRoom(String email,String roomNr, LocalDate from,LocalDate to) throws Exception {
        Reservation reservation = new Reservation();
        User user = userService.finduserByEmail(email);
        Room room = roomService.getRoomByRoomNr(roomNr);
        if(!getAvailableRooms().contains(room)) throw new Exception("Room is Not Available");
        String confirmation = "This is a confirmation email for user "
                + user.getLastName().toUpperCase() +" "+ user.getFirstName().toUpperCase()
                + " with confirmation id:- " + user.getId();

        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setFrom(from);
        reservation.setTo(to);
        reservation.setPrice(calculatePrice(roomNr,from,to));
        reservationRepository.save(reservation);
        emailService.sendEmail(user.getEmail(),"Reservation Confirmation",confirmation);
        return reservation;
    }

    private double calculatePrice(String roomNr, LocalDate from, LocalDate to) throws Exception {
        Period diff = Period.between(from,to);
        return roomService.getPriceByRoomNr(roomNr) * diff.getDays();
    }


    public Reservation findReservationByName(String firstname, String lastname) throws Exception {
        User user = userService.findUserByfullName(firstname, lastname);
        for (Reservation reservation : allReservations())
            if (reservation.getUser().equals(user))
                return reservation;

        throw new Exception("User doesn't Exit");

    }

    public Reservation findReservationByEmail(String email) throws Exception {
        User user = userService.finduserByEmail(email);
        for (Reservation reservation : allReservations())
            if (reservation.getUser().equals(user))
                return reservation;

        throw new Exception("User doesn't Exit");

    }
    public Reservation findReservationByID(String id){
        return reservationRepository.findReservationById(id);
    }


    public List<Room> getAvailableRooms(){
        List<Room> availableRooms = new ArrayList<>();
        List<String> rooms = allReservations().stream()
                .map(Reservation::getRoom)
                .map(Room::getNumber)
                .collect(Collectors.toList());
        roomService.getAllRooms().forEach(r -> {
            for (String room : rooms) {
                if (!r.getNumber().equalsIgnoreCase(room)) {
                    availableRooms.add(r);
                }
            }
        });

        return availableRooms;

    }




}


