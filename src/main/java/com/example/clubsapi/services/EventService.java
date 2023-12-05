package com.example.clubsapi.services;

import com.example.clubsapi.dto.EventDto;

public interface EventService {
    void saveEvent(String ClubName, EventDto eventDto);
    void joinEvent(String EventName);
}
