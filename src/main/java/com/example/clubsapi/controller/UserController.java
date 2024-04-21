package com.example.clubsapi.controller;

import com.example.clubsapi.dto.ChangePasswordDto;
import com.example.clubsapi.dto.ProfileDto;
import com.example.clubsapi.exception.AuthorizatoinException;
import com.example.clubsapi.services.impl.ClubsServiceImpl;
import com.example.clubsapi.services.impl.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


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
    @PostMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        Map<String, String> res = new HashMap<>();
        try{
            String message = userServices.changePassword(changePasswordDto.getUsername(), changePasswordDto.getOldPassword(), changePasswordDto.getNewPassword());

            res.put("message" , message);
            return ResponseEntity.ok(res) ;
        }
        catch (AuthorizatoinException ae){
            res.put("message", ae.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }

    }
}
