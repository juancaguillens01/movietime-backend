package com.movietime.user_service.application.services;

import com.movietime.user_service.domain.model.Role;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.domain.repositories.UserRepository;
import com.movietime.user_service.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserService getUserService;

    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = User.builder()
                .id(userId)
                .username("juanca")
                .email("juanca@example.com")
                .passwordHash("hashed123")
                .roles(Set.of(Role.ADMIN, Role.USER))
                .banned(false)
                .build();
    }

    @Test
    void getUser_returnsUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = getUserService.getUser(userId);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void getUser_throwsUserNotFoundException() {
        UUID nonExistentId = UUID.randomUUID();
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getUserService.getUser(nonExistentId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found with id: " + nonExistentId);
    }
}