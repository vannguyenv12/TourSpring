package com.vannguyen.tour.service;

import com.vannguyen.tour.entity.User;
import com.vannguyen.tour.payload.AuthResponse;
import com.vannguyen.tour.payload.LoginDto;
import com.vannguyen.tour.payload.RegisterDto;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
    AuthResponse register(RegisterDto registerDto);
    AuthResponse login(LoginDto loginDto);

    String uploadImage(MultipartFile file);

    User getCurrentUser();
}
