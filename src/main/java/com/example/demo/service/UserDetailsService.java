package com.example.demo.service;


import com.example.demo.files.dto.FileDto;
import com.example.demo.files.services.FileService;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class UserDetailsService {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserRepository userRepository;

    public UserResponseDto findOneUserById(Long userId) {
        User user = userRepository.getOne(userId);
        FileDto files = fileService.findUserFile(userId);
        return UserResponseDto.UserResponseDtoBuilder.aUserResponseDto()
                .withId(userId)
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withPassword(user.getPassword())
                .withPhoneNumber(user.getPhoneNumber())
                .withuserImages(files)
                .build();

    }

    public UserResponseDto updateUser(Long userId, UserDto dto) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id ::" +userId));

        if(Objects.nonNull(dto.getUsername())) {
            user.setUsername(dto.getUsername());
        }
        if(Objects.nonNull(dto.getEmail())) {
            user.setEmail(dto.getEmail());
        }
        if(Objects.nonNull(dto.getPassword())) {
            user.setPassword(dto.getPassword());
        }
        if(Objects.nonNull(dto.getPhoneNumber())) {
            user.setPhoneNumber(dto.getPhoneNumber());
        }

        User finalNewUser = user;

        user = userRepository.save(user);
        return findOneUserById(user.getId());
    }

    public List<UserResponseDto> findAllUser() {
        return userRepository.findAll().stream().map(buildListUserResponseDto()).collect(Collectors.toList());
    }

    private Function<User, UserResponseDto> buildListUserResponseDto() {
        return user -> findOneUserById(user.getId());
    }
}
