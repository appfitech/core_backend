package com.fitech.app.users.application.dto;

import com.fitech.app.users.domain.model.UserResponseDto;
import lombok.Data;

@Data
public class LoginResponseDto {
    private String token;
    private UserResponseDto user;
} 