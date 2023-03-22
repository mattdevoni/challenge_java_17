package com.challengeey.cruduserlogin.exceptions;

import com.challengeey.cruduserlogin.exceptions.EmailException;
import com.challengeey.cruduserlogin.exceptions.PasswordException;
import com.challengeey.cruduserlogin.persistence.exceptions.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ExceptionDTO> handleEmailException(EmailException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDTO(ex.getMessage()));
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ExceptionDTO> handlePasswordException(PasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDTO(ex.getMessage()));
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ExceptionDTO> handlePasswordException(PersistenceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionDTO(ex.getMessage()));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class ExceptionDTO {
        private String message;
    }
}