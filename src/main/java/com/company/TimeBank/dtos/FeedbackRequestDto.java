package com.company.TimeBank.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestDto {

    private Long userId;
    private Long barterTransactionId;
    private Integer rating;
    private String comment;
}
