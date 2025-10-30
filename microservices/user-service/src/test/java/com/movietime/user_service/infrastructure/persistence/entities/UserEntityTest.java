package com.movietime.user_service.infrastructure.persistence.entities;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityTest {

    @Test
    void testNoArgsConstructor() {
        UserEntity user = new UserEntity();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNull();
        assertThat(user.getUsername()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getPasswordHash()).isNull();
        assertThat(user.getRoles()).isNull();
        assertThat(user.isBanned()).isFalse();
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        RoleEntity role = new RoleEntity(1L, "ADMIN");
        Set<RoleEntity> roles = Set.of(role);

        UserEntity user = new UserEntity(id, "juanca", "juanca@example.com", "hashedPass", roles, true);

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getUsername()).isEqualTo("juanca");
        assertThat(user.getEmail()).isEqualTo("juanca@example.com");
        assertThat(user.getPasswordHash()).isEqualTo("hashedPass");
        assertThat(user.getRoles()).containsExactly(role);
        assertThat(user.isBanned()).isTrue();
    }

    @Test
    void testBuilder() {
        UUID id = UUID.randomUUID();
        RoleEntity role = new RoleEntity(2L, "USER");

        UserEntity user = UserEntity.builder()
                .id(id)
                .username("maria")
                .email("maria@example.com")
                .passwordHash("secretHash")
                .roles(Set.of(role))
                .banned(true)
                .build();

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getUsername()).isEqualTo("maria");
        assertThat(user.getEmail()).isEqualTo("maria@example.com");
        assertThat(user.getPasswordHash()).isEqualTo("secretHash");
        assertThat(user.getRoles()).containsExactly(role);
        assertThat(user.isBanned()).isTrue();
    }

    @Test
    void testSettersAndGetters() {
        UserEntity user = new UserEntity();
        UUID id = UUID.randomUUID();
        RoleEntity role = new RoleEntity(3L, "MANAGER");

        user.setId(id);
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPasswordHash("hashed123");
        user.setRoles(Set.of(role));
        user.setBanned(true);

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getUsername()).isEqualTo("john");
        assertThat(user.getEmail()).isEqualTo("john@example.com");
        assertThat(user.getPasswordHash()).isEqualTo("hashed123");
        assertThat(user.getRoles()).containsExactly(role);
        assertThat(user.isBanned()).isTrue();
    }
}