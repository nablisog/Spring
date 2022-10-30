package com.booking.hotel.repository;

import com.booking.hotel.model.Room;
import com.booking.hotel.model.RoomType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room,String> {
    Room findRoomByNumber(String number);
    List<Room> findRoomByType(RoomType type);
    boolean existsRoomByNumber(String number);
}
