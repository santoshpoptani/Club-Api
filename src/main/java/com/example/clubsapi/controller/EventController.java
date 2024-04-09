package com.example.clubsapi.controller;


import com.example.clubsapi.dto.EventDetailDto;
import com.example.clubsapi.dto.EventDto;
import com.example.clubsapi.dto.EventResponseDto;
import com.example.clubsapi.dto.EventUserResponseDto;
import com.example.clubsapi.entity.UserEntity;
import com.example.clubsapi.exception.ResousrceNotFoundException;
import com.example.clubsapi.repository.UserRepository;
import com.example.clubsapi.services.impl.EventServiceImpl;
import com.example.clubsapi.utiltiy.AccessControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private EventServiceImpl eventService;
    private UserRepository userRepository;
    private AccessControl accessControl;

    public EventController(EventServiceImpl eventService, UserRepository userRepository, AccessControl accessControl) {
        this.eventService = eventService;
        this.userRepository = userRepository;
        this.accessControl = accessControl;
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/new/{clubName}")
    public ResponseEntity<?> createEvent(@PathVariable("clubName") String Clubname,
                                         @RequestBody EventDto eventDto){

        eventService.saveEvent(Clubname,eventDto);
        Map<String,String> res = new HashMap<>();
        res.put("Message","Even Created Successfully "+eventDto.getEventname());
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @PostMapping("/join/{eventName}")
    public ResponseEntity<?> joinEvent(@PathVariable("eventName")String eventname){
        eventService.joinEvent(eventname);
        Map<String,String> res = new HashMap<>();
        res.put("Message","Event joined succesfully");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/allevents")
    public ResponseEntity<?> getallEvents(){
        List<EventResponseDto> eventResponse = eventService.getAllEvents();
        return ResponseEntity.ok(eventResponse);
    }

    @GetMapping("/event-detail/{eventId}")
    public ResponseEntity<?> getevent(@PathVariable("eventId") int eventId){
        try{
            EventDetailDto eventDetailDto =eventService.getEvent(eventId);
            return ResponseEntity.ok(eventDetailDto);
        }catch (ResousrceNotFoundException re){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(re.getMessage());
        }
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/alluserinevent")
    public ResponseEntity<?> getUsersJoinedEvent(){
        List<EventUserResponseDto> eventandUserJoinedEvens = eventService.getEventandUserJoinedEvens();
        return ResponseEntity.ok(eventandUserJoinedEvens);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PatchMapping("/update/{eventId}")
    public ResponseEntity<?> updateEvent(@PathVariable("eventId")int eventId,@RequestBody EventDto eventDto){
        if(accessControl.updateEventUtility(eventId)){
            EventDto dto = eventService.updateEvent(eventId,eventDto);
            return ResponseEntity.ok(dto);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResousrceNotFoundException("403 Error"));
        }


    }
    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable("eventId")int id){
        if(accessControl.deleteEventUtility(id)){
            eventService.deleteEvent(id);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResousrceNotFoundException("Error 401"));
        }

    }
}
