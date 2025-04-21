package com.company.TimeBank.dtos;

import lombok.Data;

@Data
public class CommentRequestDTO {
    private Long userId;
    private Long postId;
    private String content;
}