package com.example.clubsapi.utiltiy;

import com.example.clubsapi.dto.ClubDto;
import com.example.clubsapi.dto.EventDto;
import com.example.clubsapi.entity.ClubModerator;
import com.example.clubsapi.entity.Clubs;
import com.example.clubsapi.entity.Event;
import com.example.clubsapi.entity.UserEntity;
import com.example.clubsapi.exception.ResousrceNotFoundException;
import com.example.clubsapi.repository.ClubModeratorRepository;
import com.example.clubsapi.repository.EventRepository;
import com.example.clubsapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AccessControl {

    private UserRepository userRepository;
    private EventRepository eventRepository;
    private  ClubModeratorRepository clubModeratorRepository;

    public AccessControl(UserRepository userRepository, EventRepository eventRepository, ClubModeratorRepository clubModeratorRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.clubModeratorRepository = clubModeratorRepository;
    }


    public boolean checkClubDataOwner(int clubId){
        Optional<List<Integer>> clubIds = clubModeratorRepository.findClubsId(getUserFromSecurityContext().getId());
        for(Integer cl : clubIds.get()){
            if(cl == clubId) {
                return true;
            }
        }
        return false;
    }
    private UserEntity getUserFromSecurityContext(){
        String userContext= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByUsername(userContext).
                orElseThrow(()->new ResousrceNotFoundException("User not found"));

    }

    public boolean checkDataOwner(int eventId){
        Optional<List<Clubs>> clubs = clubModeratorRepository.findClubs(getUserFromSecurityContext().getId());

        for(Clubs c : clubs.get()){
            for (Event e : c.getEvents())
                if(e.getId()==eventId)
                    return true;
        }

        return false;
    }

    public  boolean joinEvent(String eventName){
        Event event = eventRepository.findByTitle(eventName).
                orElseThrow(()->new ResousrceNotFoundException("Event NOt Found"));
        List<UserEntity> userEntities = event.getClubs().getUserEntitySet().stream().toList();
        for(UserEntity user : userEntities){
            if(getUserFromSecurityContext().getUsername().equals(user.getUsername()))
                return true;
        }
        return  false;
    }
}
