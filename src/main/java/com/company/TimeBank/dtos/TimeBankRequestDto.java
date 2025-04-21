package com.company.TimeBank.dtos;


import com.company.TimeBank.enums.BarterStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBankRequestDto {


    private Long fromUserId;
    private Long toUserId;
    private String skillRequired;
    private String skillOffered;
    private Integer hoursRequired;
    private Integer hoursAvailable;
    private Integer timeExchanged;

    private BarterStatus status;
}
