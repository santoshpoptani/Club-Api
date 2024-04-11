package com.example.clubsapi.services.impl;

import com.example.clubsapi.dto.ProfileDto;
import com.example.clubsapi.dto.UserRegistrationDto;
import com.example.clubsapi.entity.*;
import com.example.clubsapi.exception.ResousrceNotFoundException;
import com.example.clubsapi.repository.ClubModeratorRepository;
import com.example.clubsapi.repository.EventRepository;
import com.example.clubsapi.repository.RoleRepository;
import com.example.clubsapi.repository.UserRepository;
import com.example.clubsapi.services.UserService;
import com.example.clubsapi.utiltiy.Helpers;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServicesImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private EventRepository eventRepository;
    private ClubModeratorRepository clubModeratorRepository;
    private Helpers helpers;

    public UserServicesImpl(UserRepository userRepository,
                            RoleRepository roleRepository,
                            Helpers helpers,
                            PasswordEncoder passwordEncoder,
                            EventRepository eventRepository,
                            ClubModeratorRepository moderatorRepository
                            ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.helpers = helpers;
        this.passwordEncoder = passwordEncoder;
        this.eventRepository = eventRepository;
        this.clubModeratorRepository = moderatorRepository;
    }

    @Override
    public void saveUser(UserRegistrationDto userRegistrationDto) {
        UserEntity user = new UserEntity();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        Set<Roles> roles = helpers.mapStringToRole(userRegistrationDto,roleRepository);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public ProfileDto getUserProfile() {
        //getting user logged in user
        String userContext= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //fetching user from Db
        UserEntity user = userRepository.findByUsername(userContext).
                orElseThrow(()->new ResousrceNotFoundException("User Not Present"));

        List<Map<String , String>> clubtitle = new ArrayList<>();
        List<Map<String, String>> eventtitle = new ArrayList<>();
        List<Map<String, List<Map<String , String>>>> clubEventsownedTitllesList = new ArrayList<>();

        //Creatinf obj of profile DTO
        ProfileDto profile = new ProfileDto();
        profile.setName(user.getUsername());

        //get counts of clubs joined by user
        profile.setClubsJoined((int) user.getClubs().stream().count());
        List<Clubs> clubs = user.getClubs().stream().toList();
        profile.setEventsJoined((int) user.getEvents().stream().count());

        //gets the name of clubs and add it into the list
        for(Clubs club : clubs){
            Map<String,String> map = new HashMap<>();
            map.put("id",String.valueOf(club.getId()));
            map.put("title",club.getTitle());
            clubtitle.add(map);
        }
        //gets events name and add to list
        List<Event> events = user.getEvents().stream().toList();
        for(Event ev :  events){
            Map<String , String> map = new HashMap<>();
            map.put("id" , String.valueOf(ev.getId()));
            map.put("title",ev.getTitle());
            eventtitle.add(map);
        }

        profile.setClubJoinedTitle(clubtitle);
        profile.setEventsJoinedTitle(eventtitle);

        // this is for the mod user details
        Optional<List<Clubs>> clubs1 = clubModeratorRepository.findClubs(user.getId());

        profile.setOwnedClub((int) clubs1.get().stream().count());

        int ownedEventCount =0;
        for(Clubs cl : clubs1.get()){
            ownedEventCount += (int) cl.getEvents().stream().count();
            profile.setOwnedEvent(ownedEventCount);
            //create the map of string and list  to get the club name as string and get list of event name r
            // related to that club
            Map<String, List<Map<String , String>>> clubTitle = new HashMap<>();
            List<Map<String , String>> eventTitle = new ArrayList<>();
            for(Event ev : cl.getEvents()){
                Map<String , String> map = new HashMap<>();
                map.put("id",String.valueOf(ev.getId()));
                map.put("title",ev.getTitle());
                eventTitle.add(map);
            }
            clubTitle.put(cl.getTitle(),eventTitle);
            clubEventsownedTitllesList.add(clubTitle);
            profile.setClubsOwnedTitle(clubEventsownedTitllesList);

        }

        return profile;
    }
}
