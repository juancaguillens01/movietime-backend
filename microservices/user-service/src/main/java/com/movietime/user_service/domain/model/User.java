package com.movietime.user_service.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class User {
    private final UUID id;
    private String username;
    private String email;
    private String passwordHash;
    private Set<Role> roles;
}