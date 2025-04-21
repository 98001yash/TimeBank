package com.company.TimeBank.service;


import com.company.TimeBank.dtos.UserMatchDTO;
import com.company.TimeBank.entities.User;
import com.company.TimeBank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SkillMatchingService {

    private final UserRepository userRepository;

    public Map<UserMatchDTO, List<UserMatchDTO>> matchUsersBySkills() {
        List<User> allUsers = userRepository.findAll();
        Map<UserMatchDTO, List<UserMatchDTO>> matches = new HashMap<>();

        for (User user : allUsers) {
            List<UserMatchDTO> potentialMatches = new ArrayList<>();
            for (User other : allUsers) {
                if (!user.getId().equals(other.getId())
                        && user.getSkillsRequired() != null
                        && other.getSkillsOffered() != null
                        && other.getSkillsOffered().toLowerCase().contains(user.getSkillsRequired().toLowerCase())
                        && user.getHoursAvailable() > 0 && other.getHoursAvailable() > 0) {

                    potentialMatches.add(convertToDTO(other));
                }
            }
            matches.put(convertToDTO(user), potentialMatches);
        }

        return matches;
    }

    private UserMatchDTO convertToDTO(User user) {
        return new UserMatchDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getSkillsOffered(),
                user.getSkillsRequired(),
                user.getHoursAvailable()
        );
    }
}
