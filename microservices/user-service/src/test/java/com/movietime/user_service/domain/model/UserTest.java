package com.movietime.user_service.domain.model;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void testBuilderCreatesUserCorrectly() {
        UUID id = UUID.randomUUID();

        User user = User.builder()
                .id(id)
                .username("juanca")
                .email("juanca@example.com")
                .passwordHash("hashed123")
                .roles(Set.of(Role.ADMIN, Role.USER))
                .build();

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getUsername()).isEqualTo("juanca");
        assertThat(user.getEmail()).isEqualTo("juanca@example.com");
        assertThat(user.getPasswordHash()).isEqualTo("hashed123");
        assertThat(user.getRoles()).containsOnly(Role.ADMIN, Role.USER);
        assertThat(user.isBanned()).isFalse();
    }

    @Test
    void testBuilderHandlesNullRoles() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .username("noRoles")
                .email("nobody@example.com")
                .passwordHash("pw")
                .build();

        assertThat(user.getRoles()).isNull();
        assertThat(user.isBanned()).isFalse();
    }

    @Test
    void testDifferentUsersCanHaveDifferentIds() {
        User u1 = User.builder().id(UUID.randomUUID()).username("a").email("a@a").passwordHash("p").build();
        User u2 = User.builder().id(UUID.randomUUID()).username("a").email("a@a").passwordHash("p").build();

        assertThat(u1.getId()).isNotNull();
        assertThat(u2.getId()).isNotNull();
        assertThat(u1.getId()).isNotEqualTo(u2.getId());
    }
}