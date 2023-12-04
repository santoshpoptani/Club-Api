package com.example.clubsapi.services.impl;

import com.example.clubsapi.dto.UserRegistrationDto;
import com.example.clubsapi.entity.ERole;
import com.example.clubsapi.entity.Roles;
import com.example.clubsapi.entity.UserEntity;
import com.example.clubsapi.repository.RoleRepository;
import com.example.clubsapi.repository.UserRepository;
import com.example.clubsapi.services.UserService;
import com.example.clubsapi.utiltiy.Helpers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServicesImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;
    private Helpers helpers;

    public UserServicesImpl(UserRepository userRepository,
                            RoleRepository roleRepository,
                            Helpers helpers,
                            PasswordEncoder passwordEncoder
                            ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.helpers = helpers;
        this.passwordEncoder = passwordEncoder;
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
}
