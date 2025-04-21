package com.company.TimeBank.service;


import com.company.TimeBank.entities.Post;
import com.company.TimeBank.entities.User;
import com.company.TimeBank.repository.PostRepository;
import com.company.TimeBank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Post createPost(Long userId, String content, String imageUrl, String category){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));

        Post post = new Post();
        post.setContent(content);
        post.setImageUrl(imageUrl);
        post.setCategory(category);
        post.setUser(user);

        return postRepository.save(post);
    }
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
}
