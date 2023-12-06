package com.example.clubsapi.services.impl;

import com.example.clubsapi.dto.ClubDto;
import com.example.clubsapi.dto.ClubResponseDto;
import com.example.clubsapi.entity.Clubs;
import com.example.clubsapi.entity.Event;
import com.example.clubsapi.entity.UserEntity;
import com.example.clubsapi.repository.ClubRepository;
import com.example.clubsapi.repository.EventRepository;
import com.example.clubsapi.repository.UserRepository;
import com.example.clubsapi.services.ClubService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClubsServiceImpl implements ClubService {
    private ClubRepository clubRepository;
    private UserRepository userRepository;
    private EventRepository eventRepository;


    public ClubsServiceImpl(ClubRepository clubRepository,UserRepository userRepository,EventRepository eventRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
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

    @Override
    public List<ClubResponseDto> findAllClubs() {
        List<ClubResponseDto> list = new ArrayList<>();
        for (Clubs club :clubRepository.findAll())
        {
            ClubResponseDto nClub = new ClubResponseDto();
            nClub.setTitle(club.getTitle());
            nClub.setContenet(club.getContent());
            nClub.setDate(club.getCreatedOn().toString());
            nClub.setEventList(club.getEvents());
            list.add(nClub);
        }
        return list;
    }
}
