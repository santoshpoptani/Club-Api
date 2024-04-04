package com.example.clubsapi.dto;

import com.example.clubsapi.entity.Event;

import java.util.List;

public class ClubDetailsDto {
    private Long id;
    private String Title;
    private String Contenet;
    private String Date;
    private List<Event> eventList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContenet() {
        return Contenet;
    }

    public void setContenet(String contenet) {
        Contenet = contenet;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
