package com.company.TimeBank.repository;

import com.company.TimeBank.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    List<Feedback> findByUserId(Long userId);

}
