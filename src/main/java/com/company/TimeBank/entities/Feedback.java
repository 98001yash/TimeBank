package com.company.TimeBank.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feedback {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barter_id")
    private TimeBank barterTransaction;

    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;


    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }

}
