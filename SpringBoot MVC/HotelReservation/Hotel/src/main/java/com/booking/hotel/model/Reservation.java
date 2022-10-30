package com.booking.hotel.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Reservation {
    @Id
    private String id;
    @DBRef
    private User user;
    @DBRef
    private Room room;
    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate from;
    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate to;

    private double price;




}
