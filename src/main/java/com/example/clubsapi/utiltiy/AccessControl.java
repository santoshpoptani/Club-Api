package com.example.clubsapi.utiltiy;

import com.example.clubsapi.dto.ClubDto;
import com.example.clubsapi.entity.UserEntity;
import com.example.clubsapi.exception.ResousrceNotFoundException;
import com.example.clubsapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccessControl {

    private UserRepository userRepository;

    public AccessControl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public boolean clubUpdateUtility(int culbId, ClubDto clubDto){

        if(getUserFromSecurityContext().getClubs().stream().toList().get(0).getId() == culbId &&
                getUserFromSecurityContext().getClubs().stream().toList().get(0).getTitle().equals( clubDto.getClubName()) ) {
            return true;
        }
        return false;
    }

    public boolean deleteClubUtility(int clubId){
        if(getUserFromSecurityContext().getClubs().stream().toList().get(0).getId() == clubId) {
            return true;
        }
        return false;

    }
    private UserEntity getUserFromSecurityContext(){
        String userContext= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user =userRepository.findByUsername(userContext).
                orElseThrow(()->new ResousrceNotFoundException("User not found"));

        return user;

    }
}
