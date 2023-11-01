package com.vannguyen.tour.controller;

import com.vannguyen.tour.entity.User;
import com.vannguyen.tour.payload.AuthResponse;
import com.vannguyen.tour.payload.LoginDto;
import com.vannguyen.tour.payload.RegisterDto;
import com.vannguyen.tour.service.IUserService;
import com.vannguyen.tour.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterDto registerDto) {
        return new ResponseEntity(userService.register(registerDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginDto loginDto) {
        return new ResponseEntity(userService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        return new ResponseEntity(userService.uploadImage(file), HttpStatus.OK);
    }
}
