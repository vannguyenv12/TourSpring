package com.vannguyen.tour.service.impl;

import com.vannguyen.tour.cloudinary.Upload;
import com.vannguyen.tour.entity.Role;
import com.vannguyen.tour.entity.User;
import com.vannguyen.tour.exception.UnauthorizeException;
import com.vannguyen.tour.payload.AuthResponse;
import com.vannguyen.tour.payload.LoginDto;
import com.vannguyen.tour.payload.RegisterDto;
import com.vannguyen.tour.repository.RoleRepository;
import com.vannguyen.tour.repository.UserRepository;
import com.vannguyen.tour.security.JwtProvider;
import com.vannguyen.tour.service.IUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final Upload upload;

    @Override
    public AuthResponse register(RegisterDto registerDto) {
        User user = this.modelMapper.map(registerDto, User.class);
        Role ROLE_USER = roleRepository.findById(1L).orElseThrow();
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(ROLE_USER);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                registerDto.getEmail(), registerDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String access_token = this.jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(savedUser.getEmail(), access_token);

        return authResponse;
    }

    @Override
    public AuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String access_token = this.jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(loginDto.getEmail(), access_token);

        return authResponse;
    }

    @Override
    public String uploadImage(MultipartFile file) {
        User user = this.getCurrentUser();
        String imageUrl = upload.uploadImageToCloudinary(file);
        user.setPhoto(imageUrl);
        return  "Upload Image Successfully: " + imageUrl;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizeException();
        }

        return userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }

}
