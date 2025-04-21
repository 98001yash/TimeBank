package com.company.TimeBank.dtos;

import lombok.Data;

@Data
public class PostRequestDTO {
    private Long userId;
    private String content;
    private String imageUrl;
    private String category;
}