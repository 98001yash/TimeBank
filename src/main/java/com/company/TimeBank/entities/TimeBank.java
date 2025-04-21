package com.company.TimeBank.entities;

import com.company.TimeBank.enums.BarterStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "time_bank")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

    private String skillOffered;
    private String skillRequired;
    private Integer timeExchanged;

    private Integer hoursRequired;

    private Integer hoursAvailable;


    @Enumerated(EnumType.STRING)
    private BarterStatus status = BarterStatus.PENDING;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Transient
    private String fromUserName;

    @Transient
    private String toUserName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accepted_by")
    private User acceptedBy;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // After saving, you can populate the user names as needed
    public void populateUserNames() {
        if (this.fromUser != null) {
            this.fromUserName = this.fromUser.getName();
        }
        if (this.toUser != null) {
            this.toUserName = this.toUser.getName();
        }
    }
}
