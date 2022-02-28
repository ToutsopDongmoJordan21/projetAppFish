package com.example.demo.models.dto;

import com.example.demo.files.dto.FileDto;

public class UserDto {

    public UserDto() {}

    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private FileDto userImages;

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public FileDto getUserImages() {
        return userImages;
    }

    public UserDto setUserImages(FileDto userImages) {
        this.userImages = userImages;
        return this;
    }
}

