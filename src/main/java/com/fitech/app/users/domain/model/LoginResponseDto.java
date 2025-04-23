package com.fitech.app.users.domain.model;

import com.fitech.app.users.domain.model.UserResponseDto;
import lombok.Data;

@Data
public class LoginResponseDto {
    private String token;
    private UserResponseDto user;
} 