package com.example.clubsapi.services;

import com.example.clubsapi.dto.ProfileDto;
import com.example.clubsapi.dto.UserRegistrationDto;

public interface UserService {
    void saveUser(UserRegistrationDto userRegistrationDto);

    ProfileDto getUserProfile();
}
