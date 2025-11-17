package com.movietime.user_service.application.services;

import com.movietime.user_service.domain.model.Role;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUsersServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUsersService getUsersService;

    private User user;

    @BeforeEach
    void setUp() {
        UUID id = UUID.randomUUID();
        user = User.builder()
                .id(id)
                .username("juanca")
                .email("juanca@example.com")
                .passwordHash("hashed123")
                .roles(Set.of(Role.ADMIN, Role.USER))
                .banned(false)
                .build();
    }

    @Test
    void getUsers_returnsListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = getUsersService.getUsers();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(user);
    }

    @Test
    void getUsers_returnsEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> result = getUsersService.getUsers();

        assertThat(result).isEmpty();
    }
}
