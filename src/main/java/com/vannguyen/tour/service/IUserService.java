package com.vannguyen.tour.service;

import com.vannguyen.tour.entity.User;
import com.vannguyen.tour.payload.AuthResponse;
import com.vannguyen.tour.payload.LoginDto;
import com.vannguyen.tour.payload.RegisterDto;

public interface IUserService {
    AuthResponse register(RegisterDto registerDto);
    AuthResponse login(LoginDto loginDto);

    User getCurrentUser();
}
