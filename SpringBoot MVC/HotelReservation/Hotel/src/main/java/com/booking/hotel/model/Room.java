package com.booking.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Room {
    @Id
    private String id;
    @Indexed(unique = true)
    private String number;
    @NotBlank
    private int beds;
    @NotBlank
    private int price;
    @NotBlank
    private RoomType type;

}
