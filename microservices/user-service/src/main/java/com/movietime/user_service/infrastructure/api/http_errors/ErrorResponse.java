package com.movietime.user_service.infrastructure.api.http_errors;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String message
) {
}