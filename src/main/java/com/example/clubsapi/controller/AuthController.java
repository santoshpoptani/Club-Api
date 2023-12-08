package com.example.clubsapi.controller;

import com.example.clubsapi.dto.LoginDto;
import com.example.clubsapi.dto.UserRegistrationDto;
import com.example.clubsapi.jwt.JwtUtil;
import com.example.clubsapi.services.impl.UserServicesImpl;
import com.example.clubsapi.services.securityService.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthenticationManager manager;
   private UserServicesImpl userServices;
   private JwtUtil jwtUtil;
   @Autowired
   private LoginDto loginDto;
   @Autowired
    public AuthController(AuthenticationManager manager, UserServicesImpl userServices, JwtUtil jwtUtil) {
        this.manager = manager;
        this.userServices = userServices;
        this.jwtUtil = jwtUtil;
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userRegistrationDto){
        userServices.saveUser(userRegistrationDto);
        Map<String,String> respose = new HashMap<>();
        respose.put("Message","User Registered Successfully");
        return ResponseEntity.ok(respose);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword())
        );
        this.loginDto.setUsername(loginDto.getUsername());
        String jwtToken = jwtUtil.generateToken(loginDto.getUsername());
        UserDetailsImp userDetailsImp =(UserDetailsImp) authentication.getPrincipal();
        Map<String,String> response = new HashMap<>();
        response.put("User", userDetailsImp.getUsername());
        response.put("Token",jwtToken);
        return ResponseEntity.ok(response);
    }
}
