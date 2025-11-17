package com.movietime.user_service.infrastructure.persistence.adapters;

import com.movietime.user_service.domain.model.Role;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.infrastructure.persistence.entities.RoleEntity;
import com.movietime.user_service.infrastructure.persistence.entities.UserEntity;
import com.movietime.user_service.infrastructure.persistence.mappers.UserMapperImpl;
import com.movietime.user_service.infrastructure.persistence.repositories.JpaRoleRepository;
import com.movietime.user_service.infrastructure.persistence.repositories.JpaUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({UserPersistenceAdapter.class, UserMapperImpl.class})
class UserPersistenceAdapterTest {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private JpaRoleRepository jpaRoleRepository;

    @Autowired
    private UserPersistenceAdapter adapter;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        jpaUserRepository.deleteAll();
        jpaRoleRepository.deleteAll();

        RoleEntity adminRole = new RoleEntity();
        adminRole.setName("ADMIN");
        RoleEntity userRole = new RoleEntity();
        userRole.setName("USER");

        jpaRoleRepository.save(adminRole);
        jpaRoleRepository.save(userRole);

        userEntity = UserEntity.builder()
                .username("juanca")
                .email("juanca@example.com")
                .passwordHash("hashed123")
                .roles(Set.of(adminRole, userRole))
                .banned(false)
                .build();

        jpaUserRepository.save(userEntity);
    }

    @Test
    void findAll_returnsMappedUsers() {
        List<User> users = adapter.findAll();

        assertThat(users).hasSize(1);

        User user = users.getFirst();
        assertThat(user.getId()).isEqualTo(userEntity.getId());
        assertThat(user.getUsername()).isEqualTo("juanca");
        assertThat(user.getEmail()).isEqualTo("juanca@example.com");
        assertThat(user.getRoles()).containsExactlyInAnyOrder(Role.ADMIN, Role.USER);
        assertThat(user.isBanned()).isFalse();
    }

    @Test
    void findAll_returnsEmptyList() {
        jpaUserRepository.deleteAll();
        List<User> users = adapter.findAll();

        assertThat(users).isEmpty();
    }

    @Test
    void findById_returnsMappedUser() {
        Optional<User> userOptional = adapter.findById(userEntity.getId());

        assertThat(userOptional).isPresent();

        User user = userOptional.get();
        assertThat(user.getId()).isEqualTo(userEntity.getId());
        assertThat(user.getUsername()).isEqualTo("juanca");
        assertThat(user.getEmail()).isEqualTo("juanca@example.com");
        assertThat(user.getRoles()).containsExactlyInAnyOrder(Role.ADMIN, Role.USER);
        assertThat(user.isBanned()).isFalse();
    }

    @Test
    void findById_returnsEmptyOptionalWhenNotFound() {
        UUID randomId = UUID.randomUUID();
        Optional<User> userOptional = adapter.findById(randomId);

        assertThat(userOptional).isEmpty();
    }
}