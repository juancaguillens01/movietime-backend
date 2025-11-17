package com.movietime.user_service.infrastructure.api.controllers;

import com.movietime.user_service.application.usecases.GetUserUseCase;
import com.movietime.user_service.application.usecases.GetUsersUseCase;
import com.movietime.user_service.config.TestSecurityConfig;
import com.movietime.user_service.domain.exceptions.UserNotFoundException;
import com.movietime.user_service.domain.model.Role;
import com.movietime.user_service.domain.model.User;
import com.movietime.user_service.infrastructure.api.dtos.UserResponse;
import com.movietime.user_service.infrastructure.api.mappers.UserWebMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.movietime.user_service.infrastructure.api.controllers.UserController.USER_ID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.movietime.user_service.infrastructure.api.controllers.UserController.USERS;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetUsersUseCase getUsersUseCase;

    @MockitoBean
    private GetUserUseCase getUserUseCase;

    @MockitoBean
    private UserWebMapper userWebMapper;

    private User user;
    private UserResponse userResponse;

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

        userResponse = new UserResponse(
                id,
                "juanca",
                "juanca@example.com",
                Set.of("ADMIN", "USER"),
                false
        );
    }

    @Test
    void getUsers_returnsListOfUsers() throws Exception {
        when(getUsersUseCase.getUsers()).thenReturn(List.of(user));
        when(userWebMapper.toResponse(user)).thenReturn(userResponse);

        mockMvc.perform(get(USERS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userResponse.id().toString()))
                .andExpect(jsonPath("$[0].username").value("juanca"))
                .andExpect(jsonPath("$[0].email").value("juanca@example.com"))
                .andExpect(jsonPath("$[0].roles").isArray())
                .andExpect(jsonPath("$[0].banned").value(false));
    }

    @Test
    void getUsers_returnsEmptyList() throws Exception {
        when(getUsersUseCase.getUsers()).thenReturn(List.of());

        mockMvc.perform(get(USERS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getUserById_returnsUser() throws Exception {
        when(getUserUseCase.getUser(user.getId())).thenReturn(user);
        when(userWebMapper.toResponse(user)).thenReturn(userResponse);

        mockMvc.perform(get(UserController.USERS + USER_ID, user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResponse.id().toString()))
                .andExpect(jsonPath("$.username").value("juanca"))
                .andExpect(jsonPath("$.email").value("juanca@example.com"))
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.banned").value(false));
    }

    @Test
    void getUserById_userNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        when(getUserUseCase.getUser(nonExistentId))
                .thenThrow(new UserNotFoundException(nonExistentId));

        mockMvc.perform(get(UserController.USERS + USER_ID, nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with id: " + nonExistentId));
    }
}