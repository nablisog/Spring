package com.expensetrackerapi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserModel {
    @NotBlank(message = "Please Enter name")
    private String name;
    @NotBlank(message = "Please Enter Email")
    @Email(message = "please enter a valid email")
    private String email;
    @NotBlank(message = "Please Enter password")
    @Size(min=5, message = "Password should be atleast 5 characters")
    private String password;
    private Long age = 0L;
}
