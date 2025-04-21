package com.company.TimeBank.service;


import com.company.TimeBank.entities.User;
import com.company.TimeBank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GamificationService {

    private final UserRepository userRepository;

    public void awardPoints(Long userId, int pointsToAdd) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Ensure non-null defaults
        if (user.getPoints() == null) user.setPoints(0);
        if (user.getLevel() == null) user.setLevel(1);
        if (user.getBadges() == null) user.setBadges(new ArrayList<>());

        // Add points
        int updatedPoints = user.getPoints() + pointsToAdd;
        user.setPoints(updatedPoints);

        // Check level
        int newLevel = updatedPoints / 100;
        if (newLevel > user.getLevel()) {
            user.setLevel(newLevel);
        }

        // Grant badges
        if (updatedPoints >= 100 && !user.getBadges().contains("Helper")) {
            user.getBadges().add("Helper");
        }
        if (updatedPoints >= 500 && !user.getBadges().contains("Super Contributor")) {
            user.getBadges().add("Super Contributor");
        }

        userRepository.save(user);
    }



    public Map<String, Object> getUserProgress(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));

        Map<String,Object> progress = new HashMap<>();
        progress.put("points", user.getPoints());
        progress.put("level", user.getLevel());
        progress.put("badges", user.getBadges());
        return progress;
    }


    public List<User> getTopUsers(int limit){
        return userRepository.findTopUsersByPoints(PageRequest.of(0,limit));
    }
}
