package com.company.TimeBank.service;


import com.company.TimeBank.entities.Like;
import com.company.TimeBank.entities.Post;
import com.company.TimeBank.entities.User;
import com.company.TimeBank.repository.LikeRepository;
import com.company.TimeBank.repository.PostRepository;
import com.company.TimeBank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Like likePost(com.company.TimeBank.dtos.LikeRequestDTO likeRequestDTO) {
        User user = userRepository.findById(likeRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(likeRequestDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        return likeRepository.save(like);
    }
}
