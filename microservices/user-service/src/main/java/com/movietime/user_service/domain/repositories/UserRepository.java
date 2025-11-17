package com.movietime.user_service.domain.repositories;

import com.movietime.user_service.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(UUID id);
}