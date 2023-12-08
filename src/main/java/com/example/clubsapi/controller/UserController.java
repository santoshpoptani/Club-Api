package com.example.clubsapi.controller;

import com.example.clubsapi.services.impl.ClubsServiceImpl;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private ClubsServiceImpl clubsService;

    public UserController(ClubsServiceImpl clubsService) {
        this.clubsService = clubsService;
    }



    @Secured({"ROLE_USER"})
    @PostMapping("/join/{clubName}")
    public ResponseEntity<?> joinClub(@PathVariable("clubName") String clubName){
            clubsService.joinClub(clubName);
        Map<String,String> resp = new HashMap<>();
        resp.put("Message","Joined the club "+clubName);
        return ResponseEntity.ok(resp);
    }

}
