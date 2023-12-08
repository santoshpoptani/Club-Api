package com.example.clubsapi.controller;

import com.example.clubsapi.dto.ClubDto;
import com.example.clubsapi.dto.ClubResponseDto;
import com.example.clubsapi.entity.Clubs;
import com.example.clubsapi.services.impl.ClubsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clubs")
public class ClubController {

    private ClubsServiceImpl clubsService;

    @Autowired
    public ClubController(ClubsServiceImpl clubsService) {
        this.clubsService = clubsService;
    }

    @PostMapping("/new")
    @Secured({"ROLE_MODERATOR"})
    public ResponseEntity<?> saveClub(@RequestBody ClubDto clubDto) {

        clubsService.saveClub(clubDto);
        Map<String, String> resp = new HashMap<>();
        resp.put("Message", "Club Registred Succesfully");
        return ResponseEntity.ok(resp);

    }

    @Secured({"ROLE_MODERATOR","ROLE_USER"})
    @GetMapping("/allclubs")
    public ResponseEntity<?> getallClubs() {

        List<ClubResponseDto> collect = clubsService.findAllClubs();

        return ResponseEntity.ok(collect);
    }

    @Secured({"ROLE_MODERATOR"})
    @DeleteMapping("/delete/{clubId}")
    public ResponseEntity<?> deleteClub(@PathVariable("clubId") int clubId) {
        clubsService.deleteclub(clubId);
        return ResponseEntity.ok().build();


    }

    @Secured({"ROLE_MODERATOR"})
    @PatchMapping("/update/{clubId}")
    public ResponseEntity<?> updateClub(@RequestBody ClubDto clubDto, @PathVariable("clubId") int clubId) {
        ClubDto cLubDto = clubsService.updateClub(clubDto, clubId);
        return ResponseEntity.ok(cLubDto);
    }

}
