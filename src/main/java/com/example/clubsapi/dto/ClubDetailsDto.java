package com.example.clubsapi.dto;

import com.example.clubsapi.entity.Event;
import com.example.clubsapi.entity.UserEntity;

import java.util.List;

public class ClubDetailsDto {
    private Long id;
    private String Title;
    private String Content;
    private String Date;

    private List<UserEntity> users;

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUser(List<UserEntity> users) {
        this.users = users;
    }

    private boolean owned = false;

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

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

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
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
