package com.example.clubsapi.controller;


import com.example.clubsapi.dto.EventDetailDto;
import com.example.clubsapi.dto.EventDto;
import com.example.clubsapi.dto.EventResponseDto;
import com.example.clubsapi.dto.EventUserResponseDto;
import com.example.clubsapi.entity.Event;
import com.example.clubsapi.entity.UserEntity;
import com.example.clubsapi.exception.ResousrceNotFoundException;
import com.example.clubsapi.repository.EventRepository;
import com.example.clubsapi.repository.UserRepository;
import com.example.clubsapi.services.impl.EventServiceImpl;
import com.example.clubsapi.utiltiy.AccessControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private EventServiceImpl eventService;
    private AccessControl accessControl;
    private EventRepository eventRepository;

    public EventController(EventServiceImpl eventService, AccessControl accessControl,EventRepository eventRepository) {
        this.eventService = eventService;
        this.accessControl = accessControl;
        this.eventRepository = eventRepository;
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
        if(accessControl.joinEvent(eventname))
        {
            if(eventService.isUserPresent(eventname)) {
                Map<String,String> res = new HashMap<>();
                res.put("Message","You Have Already Joined The Event");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
            }

            else {
                eventService.joinEvent(eventname);
                Map<String,String> res = new HashMap<>();
                res.put("Message","Event joined succesfully");
                return ResponseEntity.ok(res);
            }

        }
        else{
            Event ev = eventRepository.findByTitle(eventname).
                    orElseThrow(()->new ResousrceNotFoundException("Event Not Found"));
            String clubName = ev.getClubs().getTitle();
            Map<String,String> res = new HashMap<>();
            res.put("Message","Please join the club "+ clubName);
            return ResponseEntity.badRequest().body(res);
        }

    }

    @GetMapping("/allevents")
    public ResponseEntity<?> getallEvents(){
        List<EventResponseDto> eventResponse = eventService.getAllEvents();
        return ResponseEntity.ok(eventResponse);
    }

    @GetMapping("/event-detail/{eventId}")
    public ResponseEntity<?> getevent(@PathVariable("eventId") int eventId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
           if(authentication instanceof AnonymousAuthenticationToken){
               EventDetailDto eventDetailDto =eventService.getEvent(eventId);
               return ResponseEntity.ok(eventDetailDto);
           }
           else if(accessControl.checkDataOwner(eventId)) {
               EventDetailDto eventDetailDto =eventService.getEvent(eventId);
               eventDetailDto.setOwned(true);
               return ResponseEntity.ok(eventDetailDto);
           }
           else {
               EventDetailDto eventDetailDto =eventService.getEvent(eventId);
               return ResponseEntity.ok(eventDetailDto);
           }


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
        if(accessControl.checkDataOwner(eventId)){
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
        if(accessControl.checkDataOwner(id)){
            eventService.deleteEvent(id);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResousrceNotFoundException("Error 401"));
        }

    }
}
