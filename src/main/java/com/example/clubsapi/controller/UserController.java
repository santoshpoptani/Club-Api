package com.example.clubsapi.controller;

import com.example.clubsapi.dto.ProfileDto;
import com.example.clubsapi.services.impl.ClubsServiceImpl;
import com.example.clubsapi.services.impl.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserServicesImpl userServices;

    @Autowired
    public UserController(UserServicesImpl userServices) {
        this.userServices = userServices;
    }

    @PreAuthorize("hasRole('MODERATOR') OR hasRole('USER')")
    @GetMapping("/profile")
    public  ResponseEntity<?> getUserInfo(){
        ProfileDto userProfile = userServices.getUserProfile();
        return ResponseEntity.ok(userProfile);

    }
}
