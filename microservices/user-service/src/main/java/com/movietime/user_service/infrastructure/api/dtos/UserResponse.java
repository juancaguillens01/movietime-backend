package com.movietime.user_service.infrastructure.api.dtos;

import java.util.Set;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        Set<String> roles,
        boolean banned
) {
}