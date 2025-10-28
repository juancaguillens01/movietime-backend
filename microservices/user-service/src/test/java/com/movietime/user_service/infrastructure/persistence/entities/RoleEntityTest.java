package com.movietime.user_service.infrastructure.persistence.entities;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class RoleEntityTest {

    @Test
    void testNoArgsConstructor() {
        RoleEntity role = new RoleEntity();
        assertThat(role).isNotNull();
        assertThat(role.getId()).isNull();
        assertThat(role.getName()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        RoleEntity role = new RoleEntity(1L, "ADMIN");

        assertThat(role.getId()).isEqualTo(1L);
        assertThat(role.getName()).isEqualTo("ADMIN");
    }

    @Test
    void testBuilder() {
        RoleEntity role = RoleEntity.builder()
                .id(2L)
                .name("USER")
                .build();

        assertThat(role.getId()).isEqualTo(2L);
        assertThat(role.getName()).isEqualTo("USER");
    }

    @Test
    void testSettersAndGetters() {
        RoleEntity role = new RoleEntity();
        role.setId(10L);
        role.setName("MANAGER");

        assertThat(role.getId()).isEqualTo(10L);
        assertThat(role.getName()).isEqualTo("MANAGER");
    }
}