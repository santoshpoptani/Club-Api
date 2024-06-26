package com.example.clubsapi.services;

import com.example.clubsapi.dto.EventDetailDto;
import com.example.clubsapi.dto.EventDto;
import com.example.clubsapi.dto.EventResponseDto;
import com.example.clubsapi.dto.EventUserResponseDto;

import java.util.List;

public interface EventService {
    void saveEvent(String ClubName, EventDto eventDto);
    void joinEvent(String EventName);

    List<EventResponseDto> getAllEvents();

    List<EventUserResponseDto> getEventandUserJoinedEvens();

    EventDto updateEvent(int eventId,EventDto eventDto);

    void deleteEvent(int eventId);

    EventDetailDto getEvent(int eventId);

    boolean isUserPresent(String eventName);
}
