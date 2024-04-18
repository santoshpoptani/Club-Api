package com.example.clubsapi.controller;

import com.example.clubsapi.dto.ClubDetailsDto;
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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @PostMapping("/join/{clubName}")
    public ResponseEntity<?> joinClub(@PathVariable("clubName") String clubName){
        if(clubsService.isJoinedClub(clubName)){
            Map<String,String> resp = new HashMap<>();
            resp.put("Message","You have Already Joined the Club");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
        }
        else {
            clubsService.joinClub(clubName);
            Map<String,String> resp = new HashMap<>();
            resp.put("Message","you Have Joined " + clubName +" Club");
            return ResponseEntity.ok(resp);
        }


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

    @GetMapping("/club-detail/")
    public ResponseEntity<?> getClub( @RequestParam(value = "clubId", required = false) Integer clubId,
                                      @RequestParam(value = "clubName", required = false) String clubName){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            ClubDetailsDto club = null;

            if (clubId != null) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    club = clubsService.getClub(clubId);
                } else if (accessControl.checkClubDataOwner(clubId)) {
                    club = clubsService.getClub(clubId);
                    club.setOwned(true);
                } else {
                    club = clubsService.getClub(clubId);
                }
            } else if (clubName != null) {
                club = clubsService.getClubByName(clubName); // Assuming you have a method to fetch club by name
            }

            if (club != null) {
                return ResponseEntity.ok(club);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Club not found");
            }
        } catch (ResousrceNotFoundException resourceNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resourceNotFoundException.getMessage());
        }
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/delete/{clubId}")
    public ResponseEntity<?> deleteClub(@PathVariable("clubId") int clubId) {

        if(accessControl.checkClubDataOwner(clubId)){
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

        if( accessControl.checkClubDataOwner(clubId)){
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
