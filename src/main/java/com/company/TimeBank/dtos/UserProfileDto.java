package com.company.TimeBank.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

    private Long userId;
    private String name;
    private String email;
    private String skillsOffered;
    private String skillsRequired;
    private Integer hoursAvailable;
    private String profilePicture;
}
