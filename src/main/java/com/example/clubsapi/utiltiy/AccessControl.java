package com.example.clubsapi.utiltiy;

import com.example.clubsapi.dto.ClubDto;
import com.example.clubsapi.dto.EventDto;
import com.example.clubsapi.entity.Clubs;
import com.example.clubsapi.entity.Event;
import com.example.clubsapi.entity.UserEntity;
import com.example.clubsapi.exception.ResousrceNotFoundException;
import com.example.clubsapi.repository.EventRepository;
import com.example.clubsapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccessControl {

    private UserRepository userRepository;
    private EventRepository eventRepository;

    public AccessControl(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }



    public boolean clubUpdateUtility(int culbId, ClubDto clubDto){
        List<Clubs> clubs = getUserFromSecurityContext().getClubs().stream().toList();
        for(Clubs cl : clubs){
            if(cl.getId() == culbId) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteClubUtility(int clubId){
        if(getUserFromSecurityContext().getClubs().stream().toList().get(0).getId() == clubId) {
            return true;
        }
        return false;

    }
    private UserEntity getUserFromSecurityContext(){
        String userContext= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByUsername(userContext).
                orElseThrow(()->new ResousrceNotFoundException("User not found"));

    }

    public boolean updateEventUtility(int eventId){
        Event event = eventRepository.findById(eventId).
                orElseThrow(()->new ResousrceNotFoundException("No Event Found"));
       List<UserEntity> user =  event.getClubs().getUserEntitySet().stream().toList();
        for(UserEntity ue : user){
            if(ue.getUsername().equals( getUserFromSecurityContext()))
                return true;
        }

        return false;
    }

    public  boolean deleteEventUtility(int eventId){
        Event event = eventRepository.findById(eventId).
                orElseThrow(()->new ResousrceNotFoundException("No Event Found"));
        List<UserEntity> user =  event.getClubs().getUserEntitySet().stream().toList();
        for(UserEntity ue : user){
            if(ue.getUsername().equals( getUserFromSecurityContext()))
                return true;
        }
        return  false;
    }
}
