package com.example.clubsapi.controller;


import com.example.clubsapi.dto.EventDto;
import com.example.clubsapi.dto.EventResponseDto;
import com.example.clubsapi.dto.EventUserResponseDto;
import com.example.clubsapi.services.impl.EventServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private EventServiceImpl eventService;

    public EventController(EventServiceImpl eventService) {
        this.eventService = eventService;
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

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/join/{eventName}")
    public ResponseEntity<?> joinEvent(@PathVariable("eventName")String eventname){
        eventService.joinEvent(eventname);
        Map<String,String> res = new HashMap<>();
        res.put("Message","Event joined succesfully");
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') ")
    @GetMapping("/allevents")
    public ResponseEntity<?> getEvents(){
        List<EventResponseDto> eventResponse = eventService.getAllEvents();
        return ResponseEntity.ok(eventResponse);
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
        EventDto dto = eventService.updateEvent(eventId,eventDto);
        return ResponseEntity.ok(dto);

    }
    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable("eventId")int id){
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
}
