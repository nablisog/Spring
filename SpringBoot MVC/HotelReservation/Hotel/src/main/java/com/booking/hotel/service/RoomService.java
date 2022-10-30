package com.booking.hotel.service;

import com.booking.hotel.model.Room;
import com.booking.hotel.model.RoomType;
import com.booking.hotel.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room registerRooms(Room room) throws Exception {
        Room rooms = new Room();
        rooms.setNumber(room.getNumber());
        rooms.setBeds(room.getBeds());
        rooms.setType(room.getType());
        rooms.setPrice(room.getPrice());
        existsByNumber(room.getNumber());
        roomRepository.save(rooms);
        return rooms;
    }
    public void existsByNumber(String number) throws Exception {
        boolean exist = roomRepository.existsRoomByNumber(number);
        if(exist) {
            throw new Exception("Room Already Exists");
        }
    }

    public Room getRoomByRoomNr(String number) throws Exception {
        return roomRepository.findRoomByNumber(number);
    }


    public List<Room> getRoomByRoomtype(RoomType type) {
        return roomRepository.findRoomByType(type);
    }

    public void deleteRoom(String id) {
        roomRepository.deleteById(id);
    }


    public double getPriceByRoomNr(String roomNr) throws Exception {
        for(Room room : getAllRooms()){
            if (room.getNumber().equals(roomNr)){
                return room.getPrice();
            }
        }
            throw new Exception("Not founded");
    }


}