package com.movietime.user_service.application.usecases;

import com.movietime.user_service.domain.model.User;

import java.util.List;

public interface GetUsersUseCase {
    List<User> getUsers();
}