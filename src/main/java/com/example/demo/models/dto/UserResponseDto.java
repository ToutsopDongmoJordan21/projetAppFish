package com.example.demo.models.dto;

import com.example.demo.files.dto.FileDto;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private FileDto userImages;

    public static final class UserResponseDtoBuilder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private String phoneNumber;
        private FileDto userImages;

        private UserResponseDtoBuilder() {}

        public static UserResponseDtoBuilder aUserResponseDto() {
            return new UserResponseDtoBuilder();
        }

        public UserResponseDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserResponseDtoBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserResponseDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserResponseDtoBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserResponseDtoBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserResponseDtoBuilder withuserImages(FileDto userImages) {
            this.userImages = userImages;
            return this;
        }

        public UserResponseDto build() {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(id);
            userResponseDto.setUsername(username);
            userResponseDto.setEmail(email);
            userResponseDto.setPassword(password);
            userResponseDto.setPhoneNumber(phoneNumber);
            userResponseDto.setUserImages(userImages);
            return userResponseDto;
        }
    }
}

