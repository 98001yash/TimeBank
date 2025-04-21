package com.company.TimeBank.repository;

import com.company.TimeBank.entities.TimeBank;
import com.company.TimeBank.enums.BarterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimeBankRepository extends JpaRepository<TimeBank,Long> {

    // fina all barters by the User id (either as fromUser or to Ueer)
    List<TimeBank> findByFromUser_idOrToUser_Id(Long fromUserId, Long toUserId);

    List<TimeBank> findByStatus(BarterStatus status);

    Optional<TimeBank> findById(Long id);

    Optional<TimeBank> findByFromUser_IdAndToUser_Id(Long fromUserId, Long toUserId);
    List<TimeBank> findByFromUserIdOrToUserId(Long fromUserId, Long toUserId);

}
