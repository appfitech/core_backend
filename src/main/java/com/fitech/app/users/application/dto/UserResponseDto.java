package com.fitech.app.users.application.dto;

import com.fitech.app.users.domain.model.PersonDto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private Integer type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isEmailVerified;
    private String emailVerificationToken;
    private LocalDateTime emailTokenExpiresAt;
    private PersonDto person;
} 