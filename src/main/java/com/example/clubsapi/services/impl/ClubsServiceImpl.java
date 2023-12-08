package com.example.clubsapi.services.impl;

import com.example.clubsapi.dto.ClubDto;
import com.example.clubsapi.dto.ClubResponseDto;
import com.example.clubsapi.entity.Clubs;
import com.example.clubsapi.entity.ERole;
import com.example.clubsapi.entity.UserEntity;
import com.example.clubsapi.exception.AuthorizatoinException;
import com.example.clubsapi.exception.ResousrceNotFoundException;
import com.example.clubsapi.repository.ClubRepository;
import com.example.clubsapi.repository.EventRepository;
import com.example.clubsapi.repository.UserRepository;
import com.example.clubsapi.services.ClubService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
        Clubs clubs = new Clubs();
        clubs.setTitle(clubDto.getClubName());
        clubs.setContent(clubDto.getContent());
        clubs.setCreatedOn(LocalDate.now());
        String userContext= (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user =userRepository.findByUsername(userContext).get();
        if(user.getRoles().stream().toList().get(0).getName()!= ERole.ROLE_MODERATOR )
            throw new AuthorizatoinException("UnAuthorized");
        clubs.getUserEntitySet().add(user);
        clubRepository.save(clubs);
    }

    @Override
    public void joinClub(String clubName) {
        Clubs club = clubRepository.findByTitle(clubName)
                .orElseThrow(()->new ResousrceNotFoundException("No ClubName Found"));
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

    @Override
    public void deleteclub(int id) {
        clubRepository.findById(id)
                .orElseThrow(()->new ResousrceNotFoundException("Id is not available in DB"));
        clubRepository.deleteById(id);
    }

    @Override
    public ClubDto updateClub(ClubDto clubDto,int ClubID) {
        Clubs clubs =clubRepository.findById(ClubID)
                .orElseThrow(()->new ResousrceNotFoundException("Id is not found"));
        if(clubDto == null)
            throw new ResousrceNotFoundException("No Json Body Found");
        clubs.setTitle(clubDto.getClubName());
        clubs.setContent(clubDto.getContent());
        clubRepository.save(clubs);

        return new ClubDto(clubs.getTitle(),clubs.getContent());
    }
}
