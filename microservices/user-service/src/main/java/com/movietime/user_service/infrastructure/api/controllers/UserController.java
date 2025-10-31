package com.movietime.user_service.infrastructure.api.controllers;

import com.movietime.user_service.application.usecases.GetUsersUseCase;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.infrastructure.api.dtos.UserResponse;
import com.movietime.user_service.infrastructure.api.mappers.UserWebMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final GetUsersUseCase getUsersUseCase;
    private final UserWebMapper userWebMapper;

    public UserController(GetUsersUseCase getUsersUseCase, UserWebMapper userWebMapper) {
        this.getUsersUseCase = getUsersUseCase;
        this.userWebMapper = userWebMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<User> users = getUsersUseCase.getUsers();
        List<UserResponse> response = users.stream()
                .map(userWebMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}