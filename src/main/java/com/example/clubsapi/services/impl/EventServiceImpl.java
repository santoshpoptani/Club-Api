package com.example.clubsapi.services.impl;

import com.example.clubsapi.dto.EventDto;
import com.example.clubsapi.entity.Clubs;
import com.example.clubsapi.entity.Event;
import com.example.clubsapi.entity.UserEntity;
import com.example.clubsapi.repository.ClubRepository;
import com.example.clubsapi.repository.EventRepository;
import com.example.clubsapi.repository.UserRepository;
import com.example.clubsapi.services.EventService;
import com.example.clubsapi.services.impl.ClubsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EventServiceImpl implements EventService {

    ClubRepository clubRepository;
    EventRepository eventRepository;
    UserRepository userRepository;

    @Autowired
    public EventServiceImpl(ClubRepository clubRepository,
                            EventRepository eventRepository,
                            UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveEvent(String ClubName, EventDto eventDto) {
        Clubs clubs = clubRepository.findByTitle(ClubName).get();

        Event event = new Event();
        event.setTitle(eventDto.getEventname());
        event.setContent(eventDto.getContent());
        event.setStartDate(LocalDate.now());
        event.setEndDate(LocalDate.parse(eventDto.getEnddate()));
        event.setClubs(clubs);
        eventRepository.save(event);
    }

    @Override
    public void joinEvent(String EventName) {
        Event event = eventRepository.findByTitle(EventName).get();
        String userContext = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByName(userContext).get();
        if (user.getClubs().contains(event.getClubs()))
            event.getUserEntities().add(user);
        eventRepository.save(event);
    }


}
