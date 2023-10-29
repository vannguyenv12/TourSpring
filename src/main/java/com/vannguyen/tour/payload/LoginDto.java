package com.vannguyen.tour.payload;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginDto {
    @Email
    private String email;
    private String password;
}
