package com.example.clubsapi.dto;

import com.example.clubsapi.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;

public class EventDetailDto {
    private String EventName;
    private String Description;
    private LocalDate StartDate;
    private LocalDate EndDate;
    private int clubsId;
    private String clubName;
    private List<UserEntity> users;

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }

    public int getClubsId() {
        return clubsId;
    }

    public void setClubsId(int clubsID) {
        this.clubsId = clubsId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
