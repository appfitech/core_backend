package com.fitech.app.commons.application.exceptions;

import com.fitech.app.users.application.exception.DuplicatedUserException;
import com.fitech.app.users.application.exception.UserNotFoundException;
import com.fitech.app.users.domain.services.impl.UserServiceImpl;
import com.fitech.app.users.application.exception.InvalidPasswordException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> NotFoundExceptions(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Not Found", ex.getMessage());
        return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
        log.info(ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Internal error", "Please contact support");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> validationExceptions(Exception ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatedUserException.class)
    public ResponseEntity<Map<String, String>> duplicatedExceptions(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Duplicated", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, String>> credentialExceptions(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Wrong Credentials", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}