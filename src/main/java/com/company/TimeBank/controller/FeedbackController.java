package com.company.TimeBank.controller;


import com.company.TimeBank.advices.ApiResponse;
import com.company.TimeBank.dtos.FeedbackRequestDto;
import com.company.TimeBank.dtos.FeedbackResponseDto;
import com.company.TimeBank.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<ApiResponse<FeedbackResponseDto>> submitFeedback(@RequestBody FeedbackRequestDto  requestDto){

        FeedbackResponseDto responseDto = feedbackService.submitFeedback(requestDto);
        return ResponseEntity.ok(new ApiResponse<>(responseDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<FeedbackResponseDto>>> getFeedbacksForUser(@PathVariable Long userId) {
        List<FeedbackResponseDto> feedbacks = feedbackService.getFeedbacksForUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(feedbacks));
    }
}
