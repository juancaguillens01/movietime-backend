package com.movietime.user_service.infrastructure.persistence.adapters;

import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.domain.repositories.UserRepository;
import com.movietime.user_service.infrastructure.persistence.mappers.UserMapper;
import com.movietime.user_service.infrastructure.persistence.repositories.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserPersistenceAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    public UserPersistenceAdapter(JpaUserRepository jpaUserRepository, UserMapper userMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll()
                .stream()
                .map(userMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserRepository.findById(id)
                .map(userMapper::toDomain);
    }
}