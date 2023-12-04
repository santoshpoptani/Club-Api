package com.example.clubsapi.services;

import com.example.clubsapi.dto.ClubDto;

public interface ClubService {
    void saveClub(ClubDto clubDto);
    void joinClub(String clubName);
}
