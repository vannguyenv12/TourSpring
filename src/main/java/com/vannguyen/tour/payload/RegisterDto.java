package com.vannguyen.tour.payload;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RegisterDto {
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String password;
}
