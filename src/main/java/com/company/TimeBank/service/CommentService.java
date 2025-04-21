package com.company.TimeBank.service;


import com.company.TimeBank.entities.Comment;
import com.company.TimeBank.entities.Post;
import com.company.TimeBank.entities.User;
import com.company.TimeBank.repository.CommentRepository;
import com.company.TimeBank.repository.PostRepository;
import com.company.TimeBank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Comment addComment(Long userId,Long postId, String content){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }
}
