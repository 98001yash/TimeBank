package com.company.TimeBank.controller;


import com.company.TimeBank.advices.ApiError;
import com.company.TimeBank.advices.ApiResponse;
import com.company.TimeBank.dtos.UserMatchDTO;
import com.company.TimeBank.service.SkillMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillMatchingController {

    private final SkillMatchingService skillMatchingService;


    @GetMapping("/match")
    public ResponseEntity<ApiResponse<Map<UserMatchDTO, java.util.List<UserMatchDTO>>>> matchUsersBySkills() {
        try {
            Map<UserMatchDTO, java.util.List<UserMatchDTO>> matches = skillMatchingService.matchUsersBySkills();
            return ResponseEntity.ok(new ApiResponse<>(matches));
        } catch (Exception e) {
            ApiError apiError = ApiError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Internal Server Error")
                    .subErrors(List.of(e.getMessage()))
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(apiError));
        }
    }
}
