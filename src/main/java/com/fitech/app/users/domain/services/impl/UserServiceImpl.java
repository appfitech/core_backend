package com.fitech.app.users.domain.services.impl;

import com.fitech.app.commons.util.MapperUtil;
import com.fitech.app.commons.util.PaginationUtil;
import com.fitech.app.users.domain.services.PersonService;
import com.fitech.app.users.domain.services.UserService;
import com.fitech.app.users.domain.entities.User;
import com.fitech.app.users.infrastructure.repository.UserRepository;
import com.fitech.app.users.application.wrappers.ResultPage;
import com.fitech.app.users.application.exception.InvalidPasswordException;
import com.fitech.app.users.application.exception.UserNotFoundException;
import com.fitech.app.users.domain.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PersonService personService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PersonService personService) {
        this.userRepository = userRepository;
        this.personService = personService;
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {

        return null;
    }
    @Override
    @Transactional
    public UserDto update(Integer id, UserDto userDto){
        return null;
    }

    @Override
    public UserDto getByUsernameAndPassword(UserDto userDto) {
        Optional<User> user = userRepository.
                findByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
        if(user.isEmpty()) {
            throw new InvalidPasswordException("Username: " + userDto.getUsername());
        }
        return MapperUtil.map(user.get(), UserDto.class);
    }

    @Override
    public Boolean usernameAlreadyExistsByOrgId(String username, Integer orgId) {
        Optional<User> user = userRepository.
                findByUsername(username);
        return user.isPresent();
    }

    @Override
    public UserDto getById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User Id does not exists: " + id);
        }
        return MapperUtil.map(user.get(), UserDto.class);
    }
    @Override
    public ResultPage<UserDto> getAll(Pageable paging){
        Page<User> users = userRepository.findAll(paging);
        if(users.isEmpty()){
            throw  new UserNotFoundException("Users does not exists");
        }
        return PaginationUtil.prepareResultWrapper(users, UserDto.class);
    }

    public User getUserEntityById(Integer usedId) {
        Optional<User> existingUser = userRepository.findById(usedId);
        return existingUser.orElseThrow(() -> new UserNotFoundException("User not found with id: " + usedId));
    }
    
}
