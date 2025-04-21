package com.company.TimeBank.repository;

import com.company.TimeBank.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
