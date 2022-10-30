package com.booking.hotel.controller;


import com.booking.hotel.model.Room;
import com.booking.hotel.model.RoomType;
import com.booking.hotel.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/listOfAllRooms")
    public List<Room> allRooms(){
        return roomService.getAllRooms();
    }

    @PostMapping("/addRooms")
    public ResponseEntity<Room> addRooms(@Valid @RequestBody Room room) throws Exception {
        Room rooms = roomService.registerRooms(room);
        return new ResponseEntity<>(rooms, HttpStatus.CREATED);
    }
    @GetMapping("/fetchRoomByRoomNr/{number}")
    public ResponseEntity<Room> getRoomId(@PathVariable("number")String number) throws Exception {
        Room room = roomService.getRoomByRoomNr(number);
        return new ResponseEntity<>(room,HttpStatus.OK);

    }
    @DeleteMapping("/deleteRoomById/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable("id")String id){
        roomService.deleteRoom(id);
        return ResponseEntity.ok("Room Successfully deleted");
    }

    @GetMapping("/fetchRoomByRoomType/{type}")
    public List<Room> allRoomsByTypes(@PathVariable("type") RoomType type){
        return  roomService.getRoomByRoomtype(type);

    }


}
