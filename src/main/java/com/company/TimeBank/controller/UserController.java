package com.company.TimeBank.controller;


import com.company.TimeBank.advices.ApiResponse;
import com.company.TimeBank.dtos.UserProfileDto;
import com.company.TimeBank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateProfile(@RequestBody UserProfileDto profileDto) {
        UserProfileDto updated = userService.updateUserProfile(profileDto);
        return ResponseEntity.ok(new ApiResponse<>(updated));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileDto>> getProfile(@RequestParam Long userId) {
        UserProfileDto profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(new ApiResponse<>(profile));
    }
}
