package com.movietime.user_service.infrastructure.persistence.mappers;

import com.movietime.user_service.domain.model.Role;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.infrastructure.persistence.entities.RoleEntity;
import com.movietime.user_service.infrastructure.persistence.entities.UserEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToDomain")
    User toDomain(UserEntity entity);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToEntity")
    UserEntity toEntity(User domain, @Context Set<RoleEntity> availableRoles);

    @Named("mapRolesToDomain")
    default Set<Role> mapRolesToDomain(Set<RoleEntity> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(r -> Role.valueOf(r.getName()))
                .collect(Collectors.toSet());
    }

    @Named("mapRolesToEntity")
    default Set<RoleEntity> mapRolesToEntity(Set<Role> roles, @Context Set<RoleEntity> availableRoles) {
        if (roles == null) return null;
        return roles.stream()
                .map(role -> availableRoles.stream()
                        .filter(r -> r.getName().equals(role.name()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role)))
                .collect(Collectors.toSet());
    }
}
