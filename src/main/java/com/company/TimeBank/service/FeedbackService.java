package com.company.TimeBank.service;


import com.company.TimeBank.dtos.FeedbackRequestDto;
import com.company.TimeBank.dtos.FeedbackResponseDto;
import com.company.TimeBank.entities.Feedback;
import com.company.TimeBank.entities.TimeBank;
import com.company.TimeBank.entities.User;
import com.company.TimeBank.exceptions.ResourceNotFoundException;
import com.company.TimeBank.repository.FeedbackRepository;
import com.company.TimeBank.repository.TimeBankRepository;
import com.company.TimeBank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final TimeBankRepository timeBankRepository;
    private final ModelMapper modelMapper;

    public FeedbackResponseDto submitFeedback(FeedbackRequestDto feedbackRequestDto) {
        log.info("Submitting feedback for user ID: {} and barter ID: {}", feedbackRequestDto.getUserId(), feedbackRequestDto.getBarterTransactionId());

        User user = userRepository.findById(feedbackRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + feedbackRequestDto.getUserId()));

        TimeBank barter = timeBankRepository.findById(feedbackRequestDto.getBarterTransactionId())
                .orElseThrow(() -> new ResourceNotFoundException("Barter transaction not found with ID: " + feedbackRequestDto.getBarterTransactionId()));

        Feedback feedback = Feedback.builder()
                .user(user)
                .barterTransaction(barter)
                .rating(feedbackRequestDto.getRating())
                .comment(feedbackRequestDto.getComment())
                .build();

        Feedback saved = feedbackRepository.save(feedback);

        log.info("Feedback submitted successfully for barter ID: {}", saved.getBarterTransaction().getId());
        return modelMapper.map(saved, FeedbackResponseDto.class);
    }


    public List<FeedbackResponseDto> getFeedbacksForUser(Long userId) {
        List<Feedback> feedbacks = feedbackRepository.findByUserId(userId);
        return feedbacks.stream()
                .map(fb -> modelMapper.map(fb, FeedbackResponseDto.class))
                .collect(Collectors.toList());
    }

}
