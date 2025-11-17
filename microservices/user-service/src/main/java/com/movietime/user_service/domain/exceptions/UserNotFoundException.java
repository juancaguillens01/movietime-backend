package com.movietime.user_service.domain.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    private static final String DESCRIPTION = "User not found with id: %s";

    public UserNotFoundException(UUID id) {
        super(String.format(DESCRIPTION, id));
    }
}