package com.movietime.user_service.infrastructure.api.controllers;

import com.movietime.user_service.application.usecases.GetUserUseCase;
import com.movietime.user_service.application.usecases.GetUsersUseCase;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.infrastructure.api.dtos.UserResponse;
import com.movietime.user_service.infrastructure.api.mappers.UserWebMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(UserController.USERS)
public class UserController {
    public static final String USERS = "/users";
    public static final String USER_ID = "/{id}";
    private final GetUsersUseCase getUsersUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UserWebMapper userWebMapper;

    public UserController(GetUsersUseCase getUsersUseCase, GetUserUseCase getUserUseCase,
                          UserWebMapper userWebMapper) {
        this.getUsersUseCase = getUsersUseCase;
        this.getUserUseCase = getUserUseCase;
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

    @GetMapping(USER_ID)
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        User user = getUserUseCase.getUser(id);
        UserResponse response = userWebMapper.toResponse(user);
        return ResponseEntity.ok(response);
    }
}