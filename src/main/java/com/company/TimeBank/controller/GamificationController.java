package com.company.TimeBank.controller;

import com.company.TimeBank.advices.ApiResponse;
import com.company.TimeBank.entities.User;
import com.company.TimeBank.service.GamificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gamification")
@RequiredArgsConstructor
public class GamificationController {

    private final GamificationService gamificationService;

    @PostMapping("/award")
    public ResponseEntity<ApiResponse<String>> awardPoints(
            @RequestParam Long userId,
            @RequestParam int points
    ) {
        gamificationService.awardPoints(userId, points);
        ApiResponse<String> response = new ApiResponse<>();
        response.setTimeStamp(LocalDateTime.now());
        response.setData("Points awarded successfully");
        response.setError(null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/progress")
    public ResponseEntity<ApiResponse<Map<String,Object>>> getProgress(@RequestParam Long userId){
        Map<String,Object> progress = gamificationService.getUserProgress(userId);

        ApiResponse<Map<String,Object>> response = new ApiResponse<>();
        response.setTimeStamp(LocalDateTime.now());
        response.setData(progress);
        response.setError(null);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/top")
    public ResponseEntity<List<User>> getTopUsers(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(gamificationService.getTopUsers(limit));
    }
}
