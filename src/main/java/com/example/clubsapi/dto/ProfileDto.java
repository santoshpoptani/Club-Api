package com.example.clubsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileDto {
    private String name;
    private int clubsJoined;
    private int eventsJoined;
    private int ownedClub;
    private int ownedEvent;
    private List<Map<String, String>> clubJoinedTitle;
    private List<Map<String, String>> eventsJoinedTitle;
    private List<Map<String, List<Map<String , String>>>> clubsOwnedTitle;



}
