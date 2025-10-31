package com.movietime.user_service.domain.repositories;

import com.movietime.user_service.domain.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
}