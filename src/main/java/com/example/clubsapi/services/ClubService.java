package com.example.clubsapi.services;

import com.example.clubsapi.dto.ClubDetailsDto;
import com.example.clubsapi.dto.ClubDto;
import com.example.clubsapi.dto.ClubResponseDto;
import com.example.clubsapi.entity.Clubs;

import java.util.List;

public interface ClubService {
    void saveClub(ClubDto clubDto);
    void joinClub(String clubName);

    List<ClubResponseDto> findAllClubs();

    void deleteclub(int id);
    ClubDto updateClub(ClubDto clubDto,int ClubId);

    ClubDetailsDto getClub(int id);
}
