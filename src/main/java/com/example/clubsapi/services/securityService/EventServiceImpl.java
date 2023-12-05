package com.example.clubsapi.services.securityService;

import com.example.clubsapi.dto.EventDto;
import com.example.clubsapi.entity.Clubs;
import com.example.clubsapi.entity.Event;
import com.example.clubsapi.repository.ClubRepository;
import com.example.clubsapi.repository.EventRepository;
import com.example.clubsapi.services.EventService;
import com.example.clubsapi.services.impl.ClubsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EventServiceImpl implements EventService {

    ClubRepository clubRepository;
    EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(ClubRepository clubRepository, EventRepository eventRepository) {
        this.clubRepository = clubRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void saveEvent(String ClubName, EventDto eventDto) {
        Clubs clubs =clubRepository.findByTitle(ClubName).get();

        Event event = new Event();
        event.setTitle(eventDto.getEventname());
        event.setContent(eventDto.getContent());
        event.setStartDate(LocalDate.now());
        event.setEndDate(LocalDate.parse(eventDto.getEnddate()));
        event.setClubs(clubs);
        eventRepository.save(event);
    }
}
