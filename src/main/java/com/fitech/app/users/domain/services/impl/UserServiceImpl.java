package com.fitech.app.users.domain.services.impl;

import com.fitech.app.commons.util.MapperUtil;
import com.fitech.app.commons.util.PaginationUtil;
import com.fitech.app.users.domain.model.PersonDto;
import com.fitech.app.users.domain.model.UserLoginRequest;
import com.fitech.app.users.domain.services.PersonService;
import com.fitech.app.users.domain.services.UserService;
import com.fitech.app.users.domain.entities.User;
import com.fitech.app.users.infrastructure.repository.UserRepository;
import com.fitech.app.users.application.wrappers.ResultPage;
import com.fitech.app.users.application.exception.InvalidPasswordException;
import com.fitech.app.users.application.exception.UserNotFoundException;
import com.fitech.app.users.application.exception.DuplicatedUserException;
import com.fitech.app.users.domain.model.UserDto;
import com.fitech.app.users.domain.model.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        PersonDto savedPerson = personService.save(userDto.getPerson());
        userDto.setPerson(savedPerson);

        User user = MapperUtil.map(userDto, User.class);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        // Generate and set verification token
        generateVerificationToken(user);
        
        User savedUser = userRepository.save(user);
        return MapperUtil.map(savedUser, UserDto.class);
    }

    private void generateVerificationToken(User user) {
        // Generate a random UUID as the verification token
        String token = UUID.randomUUID().toString();
        user.setEmailVerificationToken(token);
        
        // Set token expiration to 24 hours from now
        user.setEmailTokenExpiresAt(LocalDateTime.now().plusHours(24));
        
        // Set email verification status to false
        user.setIsEmailVerified(false);
    }

    @Override
    @Transactional
    public UserDto update(Integer id, UserDto userDto) {
        // Get the existing user entity
        User existingUser = getUserEntityById(id);
        
        // Validate username if it's being changed
        if (userDto.hasDifferentUserName(existingUser.getUsername())) {
            if (usernameAlreadyExistsByOrgId(userDto.getUsername(), null)) {
                throw new DuplicatedUserException("Username already exists: " + userDto.getUsername());
            }
        }
        
        // Map DTO to entity while preserving the ID
        User updatedUser = MapperUtil.map(userDto, User.class);
        updatedUser.setId(id);  // Ensure ID is preserved
        updatedUser.setUpdatedAt(LocalDateTime.now());
        
        // Save the updated entity
        updatedUser = userRepository.save(updatedUser);
        
        // Map back to DTO and return
        return MapperUtil.map(updatedUser, UserDto.class);
    }

    @Override
    public UserResponseDto getByUsernameAndPassword(UserLoginRequest loginRequest) {
        Optional<User> user = userRepository.
                findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if(user.isEmpty()) {
            throw new InvalidPasswordException("Username: " + loginRequest.getUsername());
        }
        return MapperUtil.map(user.get(), UserResponseDto.class);
    }

    @Override
    public Boolean usernameAlreadyExistsByOrgId(String username, Integer orgId) {
        Optional<User> user = userRepository.
                findByUsername(username);
        return user.isPresent();
    }

    @Override
    public UserResponseDto getById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User Id does not exists: " + id);
        }
        return MapperUtil.map(user.get(), UserResponseDto.class);
    }

    public User getUserEntityById(Integer usedId) {
        Optional<User> existingUser = userRepository.findById(usedId);
        return existingUser.orElseThrow(() -> new UserNotFoundException("User not found with id: " + usedId));
    }
    
    @Override
    public ResultPage<UserResponseDto> getAll(Pageable paging){
        Page<User> users = userRepository.findAll(paging);
        if(users.isEmpty()){
            throw  new UserNotFoundException("Users does not exists");
        }
        return PaginationUtil.prepareResultWrapper(users, UserResponseDto.class);
    }

    @Override
    @Transactional
    public UserResponseDto verifyEmail(String token) {
        // Find user by verification token
        Optional<User> userOpt = userRepository.findByEmailVerificationToken(token);
        
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid verification token");
        }
        
        User user = userOpt.get();
        
        // Check if token is expired
        if (user.getEmailTokenExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Verification token has expired");
        }
        
        // Update user verification status
        user.setIsEmailVerified(true);
        user.setEmailVerificationToken(null);
        user.setEmailTokenExpiresAt(null);
        user.setUpdatedAt(LocalDateTime.now());
        
        // Save the updated user
        user = userRepository.save(user);
        
        // Return the updated user as DTO
        return MapperUtil.map(user, UserResponseDto.class);
    }
}
