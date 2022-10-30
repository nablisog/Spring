package com.booking.hotel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String  id;
    @Size(min = 3, max = 10,message = "First Name should contain between 3 & 10 characters")
    private String firstName;
    @Size(min = 3, max = 10, message = "Last Name should contain between 3 & 10 characters")
    private String lastName;
    @Size(min = 3, max = 10,message = "User name should contain between 3 & 10 characters")
    @Indexed(unique = true)
    private String userName;
    @Size(min = 8, max = 20, message = "Password should contain minimum 8 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Email(message = "Email should be valid")
    @Indexed(unique = true)
    private String email;
    private boolean isActive;

}
