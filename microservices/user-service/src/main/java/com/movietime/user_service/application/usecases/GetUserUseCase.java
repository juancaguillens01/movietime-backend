package com.movietime.user_service.application.usecases;

import com.movietime.user_service.domain.model.User;

import java.util.UUID;

public interface GetUserUseCase {
    User getUser(UUID id);
}