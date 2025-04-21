package com.company.TimeBank.controller;


import com.company.TimeBank.advices.ApiResponse;
import com.company.TimeBank.dtos.CommentRequestDTO;
import com.company.TimeBank.dtos.LikeRequestDTO;
import com.company.TimeBank.dtos.PostDTO;
import com.company.TimeBank.dtos.PostRequestDTO;
import com.company.TimeBank.entities.Comment;
import com.company.TimeBank.entities.Like;
import com.company.TimeBank.entities.Post;
import com.company.TimeBank.service.CommentService;
import com.company.TimeBank.service.LikeService;
import com.company.TimeBank.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {

    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;

    @PostMapping("/post")
    public ResponseEntity<ApiResponse<Post>> createPost(@RequestBody PostRequestDTO postRequest) {
        Post createdPost = postService.createPost(
                postRequest.getUserId(),
                postRequest.getContent(),
                postRequest.getImageUrl(),
                postRequest.getCategory()
        );
        return ResponseEntity.ok(new ApiResponse<>(createdPost));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostDTO>>> getAllPosts() {
        List<PostDTO> postDTOs = postService.getAllPosts().stream()
                .map(post -> {
                    PostDTO postDTO = new PostDTO();
                    postDTO.setId(post.getId());
                    postDTO.setContent(post.getContent());
                    postDTO.setImageUrl(post.getImageUrl());
                    postDTO.setCategory(post.getCategory());
                    postDTO.setCreatedAt(post.getCreatedAt());
                    postDTO.setUserName(post.getUser().getName());  // Only set user name to avoid deep nesting
                    return postDTO;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ApiResponse<>(postDTOs));
    }

    @PostMapping("/comment")
    public ResponseEntity<ApiResponse<Comment>> addComment(@RequestBody CommentRequestDTO requestDTO) {
        Comment comment = commentService.addComment(
                requestDTO.getUserId(),
                requestDTO.getPostId(),
                requestDTO.getContent()
        );
        return ResponseEntity.ok(new ApiResponse<>(comment));
    }


    @PostMapping("/likePost")
    public ResponseEntity<ApiResponse<String>> likePost(@RequestBody LikeRequestDTO likeRequestDTO) {
        likeService.likePost(likeRequestDTO);

        // Return ApiResponse with a success message
        ApiResponse<String> response = new ApiResponse<>("Post liked successfully");
        return ResponseEntity.ok(response);
    }
}
