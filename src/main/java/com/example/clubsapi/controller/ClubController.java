package com.example.clubsapi.controller;

import com.example.clubsapi.dto.ClubDto;
import com.example.clubsapi.services.impl.ClubsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/clubs")
public class ClubController {

    private ClubsServiceImpl clubsService;

    @Autowired
    public ClubController(ClubsServiceImpl clubsService) {
        this.clubsService = clubsService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> saveClub(@RequestBody ClubDto clubDto){

        clubsService.saveClub(clubDto);
        Map<String,String> resp = new HashMap<>();
        resp.put("Message","Club Registred Succesfully");
        return ResponseEntity.ok(resp);

    }

}
