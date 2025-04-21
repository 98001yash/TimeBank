package com.company.TimeBank.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponseDto {

    private Long id;
    private Long userId;
    private Long barterTransactionId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
