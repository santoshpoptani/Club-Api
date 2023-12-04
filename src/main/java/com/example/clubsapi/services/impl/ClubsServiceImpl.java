package com.example.clubsapi.services.impl;

import com.example.clubsapi.dto.ClubDto;
import com.example.clubsapi.entity.Clubs;
import com.example.clubsapi.entity.UserEntity;
import com.example.clubsapi.repository.ClubRepository;
import com.example.clubsapi.repository.UserRepository;
import com.example.clubsapi.services.ClubService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class ClubsServiceImpl implements ClubService {
    private ClubRepository clubRepository;
    private UserRepository userRepository;


    public ClubsServiceImpl(ClubRepository clubRepository,UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveClub(ClubDto clubDto) {
        //TODO: get username from securitycontext
        Clubs clubs = new Clubs();
        clubs.setTitle(clubDto.getClubName());
        clubs.setContent(clubDto.getContent());
        clubs.setCreatedOn(LocalDate.now());
        String userContext= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user =userRepository.findByUsername(userContext).get();
        clubs.getUserEntitySet().add(user);
        clubRepository.save(clubs);
    }

    @Override
    public void joinClub(String clubName) {
        Clubs club = clubRepository.findByTitle(clubName).get();
        String userContext= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByUsername(userContext).get();
        club.getUserEntitySet().add(user);
        clubRepository.save(club);
    }
}
