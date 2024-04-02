package com.example.clubsapi.utiltiy;

import com.example.clubsapi.dto.UserRegistrationDto;
import com.example.clubsapi.entity.ERole;
import com.example.clubsapi.entity.Roles;
import com.example.clubsapi.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Helpers {

    public Set<Roles> mapStringToRole(UserRegistrationDto userRegistrationDto, RoleRepository roleRepository){
        Set<Roles> roles = new HashSet<>();
        if (userRegistrationDto.getRoles() == null || userRegistrationDto.getRoles().isEmpty()) {
            Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role not Found"));
            roles.add(userRole);
        } else {
            userRegistrationDto.getRoles().forEach(role -> {
                switch (role) {
                    case "mod":
                        Roles modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Role not Found"));
                        roles.add(modRole);
                        break;
                    case "user":
                        Roles user = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Role not Found"));
                        roles.add(user);
                        break;
                    default:
                        Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Role not found"));
                        roles.add(userRole);
                }
            });

        }
        return roles;
    }
}
