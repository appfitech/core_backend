package com.fitech.app.users.application.controllers;

import com.fitech.app.users.domain.model.LoginRequestDto;
import com.fitech.app.users.domain.model.LoginResponseDto;
import com.fitech.app.users.domain.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/app/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        LoginResponseDto response = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-availability")
    public ResponseEntity<Map<String, Boolean>> checkAvailability(
            @RequestParam String username,
            @RequestParam String email) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("usernameExists", userService.usernameAlreadyExistsByOrgId(username, null));
        response.put("emailExists", userService.emailAlreadyExists(email));
        return ResponseEntity.ok(response);
    }
} 