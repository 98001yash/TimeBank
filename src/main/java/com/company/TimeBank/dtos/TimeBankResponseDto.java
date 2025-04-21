package com.company.TimeBank.dtos;


import com.company.TimeBank.enums.BarterStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBankResponseDto {

    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private String fromUserName;
    private String toUserName;
    private String skillOffered;
    private String skillRequired;
    private Integer timeExchanged;
    private BarterStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
