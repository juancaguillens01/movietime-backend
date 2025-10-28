package com.movietime.user_service.infrastructure.persistence.mappers;

import com.movietime.user_service.domain.model.Role;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.infrastructure.persistence.entities.RoleEntity;
import com.movietime.user_service.infrastructure.persistence.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = UserMapper.INSTANCE;

    private UUID userId;
    private Set<RoleEntity> allRoles;
    private RoleEntity adminRoleEntity;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        adminRoleEntity = RoleEntity.builder().id(1L).name("ADMIN").build();
        RoleEntity userRoleEntity = RoleEntity.builder().id(2L).name("USER").build();
        allRoles = Set.of(adminRoleEntity, userRoleEntity);
    }

    @Test
    void toDomain_shouldReturnNull_whenEntityIsNull() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    void toDomain_shouldMapEntityToDomainUser_successfully() {
        UserEntity entity = UserEntity.builder()
                .id(userId)
                .username("testuser")
                .email("test@mail.com")
                .passwordHash("hash123")
                .roles(allRoles)
                .build();

        User domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(userId, domain.getId());
        assertEquals("testuser", domain.getUsername());
        assertTrue(domain.getRoles().contains(Role.ADMIN));
        assertTrue(domain.getRoles().contains(Role.USER));
    }

    @Test
    void toEntity_shouldReturnNull_whenDomainIsNull() {
        assertNull(mapper.toEntity(null, allRoles));
    }

    @Test
    void toEntity_shouldMapDomainUserToEntity_successfully() {
        User domain = User.builder()
                .id(userId)
                .username("newuser")
                .email("new@mail.com")
                .passwordHash("newhash")
                .roles(Set.of(Role.USER))
                .build();

        UserEntity entity = mapper.toEntity(domain, allRoles);

        assertNotNull(entity);
        assertEquals(userId, entity.getId());
        assertEquals("newuser", entity.getUsername());
        Set<String> entityRoleNames = entity.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());
        assertTrue(entityRoleNames.contains("USER"));
    }

    @Test
    void toEntity_shouldThrowException_whenRoleIsNotFound() {
        Set<RoleEntity> availableRoles = Set.of(adminRoleEntity);

        User domain = User.builder()
                .id(userId)
                .username("baduser")
                .email("bad@mail.com")
                .passwordHash("hash")
                .roles(Set.of(Role.USER))
                .build();

        assertThrows(RuntimeException.class, () -> {
            mapper.toEntity(domain, availableRoles);
        });
    }
}