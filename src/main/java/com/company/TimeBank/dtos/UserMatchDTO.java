package com.company.TimeBank.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMatchDTO {
    private Long id;
    private String name;
    private String email;
    private String skillsOffered;
    private String skillsRequired;
    private Integer hoursAvailable;
}
