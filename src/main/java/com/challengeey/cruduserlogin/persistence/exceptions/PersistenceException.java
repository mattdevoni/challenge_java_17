package com.challengeey.cruduserlogin.persistence.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersistenceException extends RuntimeException {
    private String code;
    private String message;
}
