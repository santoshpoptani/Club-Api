package com.example.clubsapi.services.impl;

import com.example.clubsapi.dto.EventDto;
import com.example.clubsapi.dto.EventResponseDto;
import com.example.clubsapi.dto.EventUserResponseDto;
import com.example.clubsapi.entity.Clubs;
import com.example.clubsapi.entity.ERole;
import com.example.clubsapi.entity.Event;
import com.example.clubsapi.entity.UserEntity;
import com.example.clubsapi.exception.AuthorizatoinException;
import com.example.clubsapi.exception.ResousrceNotFoundException;
import com.example.clubsapi.repository.ClubRepository;
import com.example.clubsapi.repository.EventRepository;
import com.example.clubsapi.repository.UserRepository;
import com.example.clubsapi.services.EventService;
import com.example.clubsapi.services.impl.ClubsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Clubs clubs = clubRepository.findByTitle(ClubName)
                .orElseThrow(()->new ResousrceNotFoundException("Club not Found"));

        String usercontex = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity user = userRepository.findByUsername(usercontex).
                orElseThrow(()->new ResousrceNotFoundException("User is not found"));

        if(user.getRoles().stream().toList().get(0).getName()!= ERole.ROLE_MODERATOR)
            throw new AuthorizatoinException("UnAuthorized user");

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
        Event event = eventRepository.findByTitle(EventName)
                .orElseThrow(()->new ResousrceNotFoundException("Event not found"));
        String userContext = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByName(userContext).get();
        if (user.getClubs().contains(event.getClubs()))
            event.getUserEntities().add(user);
        eventRepository.save(event);
    }

    @Override
    public List<EventResponseDto> getAllEvents() {
        List<EventResponseDto> collect = eventRepository.findAll().stream()
                .map((event ->
                        new EventResponseDto(
                                event.getTitle(),
                                event.getContent(),
                                event.getStartDate(),
                                event.getEndDate(),
                                event.getClubs().getId()
                        )
                )).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<EventUserResponseDto> getEventandUserJoinedEvens() {
        List<EventUserResponseDto> eventUserResponseDtoStream = eventRepository.findAll().stream()
                .map(event -> new EventUserResponseDto(
                        event.getTitle(),
                        event.getContent(),
                        event.getStartDate(),
                        event.getEndDate(),
                        event.getClubs().getId(),
                        event.getUserEntities().stream().toList()
                )).collect(Collectors.toList());
        return eventUserResponseDtoStream;
    }

    @Override
    public EventDto updateEvent(int eventId, EventDto eventDto) {
       Event event= eventRepository.findById(eventId)
               .orElseThrow(()->new ResousrceNotFoundException("EventId Not Found"));
       event.setTitle(eventDto.getEventname());
       event.setContent(eventDto.getContent());
       event.setEndDate(LocalDate.parse(eventDto.getEnddate()));
       eventRepository.save(event);
        return new EventDto(event.getTitle(),event.getContent(),event.getEndDate().toString());
    }

    @Override
    public void deleteEvent(int eventId) {
        eventRepository.deleteById(eventId);
    }


}
