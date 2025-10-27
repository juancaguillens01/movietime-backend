package com.movietime.user_service.infrastructure.persistence.mappers;

import com.movietime.user_service.domain.model.Role;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.infrastructure.persistence.entities.RoleEntity;
import com.movietime.user_service.infrastructure.persistence.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default User toDomain(UserEntity entity) {
        if (entity == null) return null;

        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .roles(entity.getRoles().stream()
                        .map(r -> Role.valueOf(r.getName()))
                        .collect(Collectors.toSet()))
                .build();
    }

    default UserEntity toEntity(User domain, Set<RoleEntity> availableRoles) {
        if (domain == null) return null;

        Set<RoleEntity> roles = domain.getRoles().stream()
                .map(role -> availableRoles.stream()
                        .filter(r -> r.getName().equals(role.name()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role)))
                .collect(Collectors.toSet());

        return UserEntity.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .email(domain.getEmail())
                .passwordHash(domain.getPasswordHash())
                .roles(roles)
                .build();
    }
}