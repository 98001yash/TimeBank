package com.company.TimeBank.service;


import com.company.TimeBank.dtos.UserProfileDto;
import com.company.TimeBank.entities.User;
import com.company.TimeBank.exceptions.ResourceNotFoundException;
import com.company.TimeBank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with email: "+username));
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id: "+id));
    }

    public UserProfileDto updateUserProfile(UserProfileDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + dto.getUserId()));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setSkillsOffered(dto.getSkillsOffered());
        user.setSkillsRequired(dto.getSkillsRequired());
        user.setHoursAvailable(dto.getHoursAvailable());
        user.setProfilePicture(dto.getProfilePicture());

        User updatedUser = userRepository.save(user);

        return new UserProfileDto(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getSkillsOffered(),
                updatedUser.getSkillsRequired(),
                updatedUser.getHoursAvailable(),
                updatedUser.getProfilePicture()
        );
    }


    public UserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        return new UserProfileDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getSkillsOffered(),
                user.getSkillsRequired(),
                user.getHoursAvailable(),
                user.getProfilePicture()
        );
    }




}
