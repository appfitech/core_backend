package com.fitech.app.users.application.controllers;

import com.fitech.app.commons.application.controllers.BaseController;
import com.fitech.app.commons.util.PaginationUtil;
import com.fitech.app.users.application.wrappers.ResultPage;
import com.fitech.app.users.domain.model.UserDto;
import com.fitech.app.users.domain.model.UserResponseDto;
import com.fitech.app.users.domain.model.UserLoginRequest;
import com.fitech.app.users.domain.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

@RequestMapping("v1/app/user")
@RestController
public class UserController extends BaseController {
    private final UserService userService;
    private static final Logger log = Logger.getLogger(UserController.class.getName());
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
    public ResponseEntity<UserResponseDto> authenticate(@Valid @RequestBody UserLoginRequest loginRequest){
        log.info("Login request: " + loginRequest);
        UserResponseDto userDto = userService.getByUsernameAndPassword(loginRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<UserResponseDto> getById(@PathVariable(value = "id") Integer id){
        UserResponseDto userDto = userService.getById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto userDto){
        userDto = userService.save(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> update(@PathVariable(value = "id") int id, @Valid @RequestBody UserDto userDto){
        userDto = userService.update(id, userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Map<String,Object>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size){
        Pageable paging = PageRequest.of(page-1, size);
        ResultPage<UserResponseDto> resultPageWrapper = userService.getAll(paging);
        Map<String, Object> response = prepareResponse(resultPageWrapper);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/verify-email", produces = "application/json")
    public ResponseEntity<UserResponseDto> verifyEmail(@RequestParam String token) {
        log.info("Email verification request for token: " + token);
        UserResponseDto userDto = userService.verifyEmail(token);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Override
    protected String getResource() {
        return "users";
    }
}
