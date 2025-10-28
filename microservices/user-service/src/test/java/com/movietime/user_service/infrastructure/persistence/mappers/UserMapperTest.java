package com.movietime.user_service.infrastructure.persistence.mappers;

import com.movietime.user_service.domain.model.Role;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.infrastructure.persistence.entities.RoleEntity;
import com.movietime.user_service.infrastructure.persistence.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserMapperTest {

    private UserMapper mapper;
    private Set<RoleEntity> availableRoles;

    @BeforeEach
    void setUp() {
        mapper = UserMapper.INSTANCE;
        availableRoles = Set.of(
                new RoleEntity(1L, "ADMIN"),
                new RoleEntity(2L, "USER")
        );
    }

    @Test
    void testToDomain() {
        UUID id = UUID.randomUUID();
        UserEntity entity = UserEntity.builder()
                .id(id)
                .username("juanca")
                .email("juanca@example.com")
                .passwordHash("hashed123")
                .roles(availableRoles)
                .build();

        User user = mapper.toDomain(entity);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getUsername()).isEqualTo("juanca");
        assertThat(user.getEmail()).isEqualTo("juanca@example.com");
        assertThat(user.getPasswordHash()).isEqualTo("hashed123");
        assertThat(user.getRoles()).containsExactlyInAnyOrder(Role.ADMIN, Role.USER);
    }

    @Test
    void testToDomainWithNull() {
        assertThat(mapper.toDomain(null)).isNull();
    }

    @Test
    void testToEntity() {
        UUID id = UUID.randomUUID();
        User domain = User.builder()
                .id(id)
                .username("juanca")
                .email("juanca@example.com")
                .passwordHash("hashed123")
                .roles(Set.of(Role.ADMIN, Role.USER))
                .build();

        UserEntity entity = mapper.toEntity(domain, availableRoles);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getUsername()).isEqualTo("juanca");
        assertThat(entity.getEmail()).isEqualTo("juanca@example.com");
        assertThat(entity.getPasswordHash()).isEqualTo("hashed123");
        assertThat(entity.getRoles()).extracting(RoleEntity::getName)
                .containsExactlyInAnyOrder("ADMIN", "USER");
    }

    @Test
    void testToEntityWithNull() {
        assertThat(mapper.toEntity(null, availableRoles)).isNull();
    }

    @Test
    void testToEntityThrowsWhenRoleNotInAvailableRoles() {
        User domain = User.builder()
                .id(UUID.randomUUID())
                .username("juanca")
                .email("juanca@example.com")
                .passwordHash("hashed123")
                .roles(Set.of(Role.ADMIN, Role.USER))
                .build();

        Set<RoleEntity> limitedRoles = Set.of(new RoleEntity(1L, "ADMIN"));

        assertThatThrownBy(() -> mapper.toEntity(domain, limitedRoles))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Role not found: USER");
    }

}
