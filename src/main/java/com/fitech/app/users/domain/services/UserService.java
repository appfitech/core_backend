package com.fitech.app.users.domain.services;


import com.fitech.app.users.application.wrappers.ResultPage;
import com.fitech.app.users.domain.model.UserDto;
import com.fitech.app.users.domain.entities.User;
import com.fitech.app.users.domain.model.UserLoginRequest;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto save(UserDto userDto);
    UserDto update(Integer id, UserDto dto);
    UserDto getByUsernameAndPassword(UserLoginRequest loginRequest);
    Boolean usernameAlreadyExistsByOrgId(String username, Integer orgId);
    UserDto getById(Integer id);
    User getUserEntityById(Integer id);
    ResultPage<UserDto> getAll(Pageable paging);
}
