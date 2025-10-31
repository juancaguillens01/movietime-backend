package com.movietime.user_service.application.services;

import com.movietime.user_service.application.usecases.GetUsersUseCase;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUsersService implements GetUsersUseCase {

    private final UserRepository userRepository;

    public GetUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}