package com.company.TimeBank.repository;


import com.company.TimeBank.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u ORDER BY u.points DESC")
    List<User> findTopUsersByPoints(Pageable pageable);

}
