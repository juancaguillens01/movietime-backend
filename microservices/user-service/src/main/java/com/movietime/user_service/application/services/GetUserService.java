package com.movietime.user_service.application.services;

import com.movietime.user_service.application.usecases.GetUserUseCase;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.domain.repositories.UserRepository;
import com.movietime.user_service.domain.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserService implements GetUserUseCase {

    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
