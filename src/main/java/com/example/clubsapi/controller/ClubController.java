package com.example.clubsapi.controller;

import com.example.clubsapi.dto.ClubDto;
import com.example.clubsapi.dto.ClubResponseDto;
import com.example.clubsapi.entity.Clubs;
import com.example.clubsapi.entity.UserEntity;
import com.example.clubsapi.exception.ResousrceNotFoundException;
import com.example.clubsapi.repository.UserRepository;
import com.example.clubsapi.services.impl.ClubsServiceImpl;
import com.example.clubsapi.utiltiy.AccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clubs")
public class ClubController {

    private ClubsServiceImpl clubsService;
    private UserRepository userRepository;
    private AccessControl accessControl;

    @Autowired
    public ClubController(ClubsServiceImpl clubsService, UserRepository userRepository, AccessControl accessControl) {
        this.clubsService = clubsService;
        this.userRepository = userRepository;
        this.accessControl = accessControl;
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> saveClub(@RequestBody ClubDto clubDto) {

        clubsService.saveClub(clubDto);
        Map<String, String> resp = new HashMap<>();
        resp.put("Message", "Club Registred Succesfully");
        return ResponseEntity.ok(resp);

    }


    @GetMapping("/allclubs")
    public ResponseEntity<?> getallClubs() {

        List<ClubResponseDto> collect = clubsService.findAllClubs();

        return ResponseEntity.ok(collect);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/delete/{clubId}")
    public ResponseEntity<?> deleteClub(@PathVariable("clubId") int clubId) {

        if(accessControl.deleteClubUtility(clubId)){
            clubsService.deleteclub(clubId);
            return ResponseEntity.status(HttpStatus.OK).body("Club Deleted");
        }
        else{
            Map<String,String> error = new HashMap<>();
            error.put("error code ", "403");
            error.put("Message","Your are forbidden to access the records");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PatchMapping("/update/{clubId}")
    public ResponseEntity<?> updateClub(@RequestBody ClubDto clubDto, @PathVariable("clubId") int clubId) {

        if( accessControl.clubUpdateUtility(clubId,clubDto)){
            ClubDto cLubDto = clubsService.updateClub(clubDto, clubId);
            return ResponseEntity.ok(cLubDto);
        }
        else {
            Map<String,String> error = new HashMap<>();
            error.put("error code ", "403");
            error.put("Message","Your are forbidden to access the records");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }


    }

}
