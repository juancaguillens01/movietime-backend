package com.movietime.user_service.infrastructure.api.mappers;

import com.movietime.user_service.domain.model.Role;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.infrastructure.api.dtos.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserWebMapperTest {

    private UserWebMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UserWebMapper.class);
    }

    @Test
    void testToResponse() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .username("juanca")
                .email("juanca@example.com")
                .roles(Set.of(Role.ADMIN, Role.USER))
                .banned(true)
                .build();

        UserResponse response = mapper.toResponse(user);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(id);
        assertThat(response.username()).isEqualTo("juanca");
        assertThat(response.email()).isEqualTo("juanca@example.com");
        assertThat(response.roles()).containsExactlyInAnyOrder("ADMIN", "USER");
        assertThat(response.banned()).isTrue();
    }

    @Test
    void testToResponseWithNullRoles() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .username("juanca")
                .email("juanca@example.com")
                .roles(null)
                .banned(false)
                .build();

        UserResponse response = mapper.toResponse(user);

        assertThat(response).isNotNull();
        assertThat(response.roles()).isNull();
    }
}