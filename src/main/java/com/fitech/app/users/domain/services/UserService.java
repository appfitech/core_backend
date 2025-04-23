package com.fitech.app.users.domain.services;

import com.fitech.app.users.domain.model.LoginResponseDto;
import com.fitech.app.users.application.dto.ResultPage;
import com.fitech.app.users.domain.model.UserDto;
import com.fitech.app.users.domain.model.UserResponseDto;
import com.fitech.app.users.domain.entities.User;
import com.fitech.app.users.domain.model.UserLoginRequest;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto save(UserDto userDto);
    UserDto update(Integer id, UserDto dto);
    UserResponseDto getByUsernameAndPassword(UserLoginRequest loginRequest);
    Boolean usernameAlreadyExistsByOrgId(String username, Integer orgId);
    UserResponseDto getById(Integer id);
    User getUserEntityById(Integer id);
    ResultPage<UserResponseDto> getAll(Pageable paging);
    UserResponseDto verifyEmail(String token);
    LoginResponseDto login(String username, String password);
}
