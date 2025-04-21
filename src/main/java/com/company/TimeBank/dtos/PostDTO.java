package com.company.TimeBank.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Long id;
    private String content;
    private String imageUrl;
    private String category;
    private LocalDateTime createdAt;
    private String userName;  // Only return essential fields for the user
}
