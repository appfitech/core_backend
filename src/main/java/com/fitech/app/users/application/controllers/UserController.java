package com.fitech.app.users.application.controllers;

import com.fitech.app.commons.application.controllers.BaseController;
import com.fitech.app.users.domain.model.LoginRequestDto;
import com.fitech.app.users.domain.model.LoginResponseDto;
import com.fitech.app.users.application.exception.UserNotFoundException;
import com.fitech.app.users.application.dto.ResultPage;
import com.fitech.app.users.domain.model.UserDto;
import com.fitech.app.users.domain.model.UserResponseDto;
import com.fitech.app.users.domain.services.UserService;
import com.fitech.app.users.infrastructure.email.EmailService;
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
    private final EmailService emailService;
    private static final Logger log = Logger.getLogger(UserController.class.getName());
    
    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            LoginResponseDto userResponseDto = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(userResponseDto);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        try {
            UserResponseDto user = userService.verifyEmail(token);
            return ResponseEntity.ok(Map.of(
                "message", "Email verificado exitosamente",
                "user", user
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/test-email")
    public ResponseEntity<?> testEmail(@RequestParam String email) {
        try {
            emailService.sendTestEmail(email);
            return ResponseEntity.ok(Map.of(
                "message", "Email de prueba enviado exitosamente a " + email
            ));
        } catch (Exception e) {
            log.severe("Error al enviar email de prueba: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Error al enviar el email: " + e.getMessage()
            ));
        }
    }

    @Override
    protected String getResource() {
        return "users";
    }
}
