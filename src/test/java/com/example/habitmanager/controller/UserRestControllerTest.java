package com.example.habitmanager.controller;

import com.example.habitmanager.entity.User;
import com.example.habitmanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    @Test
    void createUserAcceptsNumericAge() {
        User saved = new User("Alex", 21, "Walk 10,000 steps daily", LocalDate.now(), LocalDate.now().plusDays(30));
        when(userService.createUser("Alex", 21)).thenReturn(saved);

        ResponseEntity<User> response = userRestController.createUser(Map.of("name", "Alex", "age", 21));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Alex", response.getBody().getName());
        verify(userService).createUser("Alex", 21);
    }

    @Test
    void createUserAcceptsStringAge() {
        User saved = new User("Mia", 19, "Walk 10,000 steps daily", LocalDate.now(), LocalDate.now().plusDays(30));
        when(userService.createUser("Mia", 19)).thenReturn(saved);

        ResponseEntity<User> response = userRestController.createUser(Map.of("name", "Mia", "age", "19"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mia", response.getBody().getName());
        verify(userService).createUser("Mia", 19);
    }

    @Test
    void createUserRejectsBlankName() {
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> userRestController.createUser(Map.of("name", "   ", "age", 20))
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("400 BAD_REQUEST \"'name' is required\"", ex.getMessage());
    }

    @Test
    void createUserRejectsInvalidAgeString() {
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> userRestController.createUser(Map.of("name", "Alex", "age", "abc"))
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("400 BAD_REQUEST \"'age' must be a number\"", ex.getMessage());
    }

    @Test
    void createUserRejectsMissingAge() {
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> userRestController.createUser(Map.of("name", "Alex"))
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("400 BAD_REQUEST \"'age' is required and must be a number\"", ex.getMessage());
    }

    @Test
    void getAllUsersReturnsList() {
        when(userService.getAllUsers()).thenReturn(List.of(new User(), new User()));

        ResponseEntity<List<User>> response = userRestController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getUserByIdFoundReturnsOk() {
        User user = new User();
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userRestController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void getUserByIdMissingReturnsNotFound() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userRestController.getUserById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateUserReturnsUpdatedUser() {
        User updated = new User("Neo", 33, "Walk 10,000 steps daily", LocalDate.now(), LocalDate.now().plusDays(30));
        when(userService.updateUser(7L, "Neo", 33)).thenReturn(updated);

        ResponseEntity<User> response = userRestController.updateUser(7L, Map.of("name", "Neo", "age", 33));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
    }

    @Test
    void updateScoreReturnsUpdatedUser() {
        User updated = new User("Neo", 33, "Walk 10,000 steps daily", LocalDate.now(), LocalDate.now().plusDays(30));
        updated.setScore(100);
        when(userService.updateUserScore(7L, 20)).thenReturn(updated);

        ResponseEntity<User> response = userRestController.updateScore(7L, 20);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100, response.getBody().getScore());
    }

    @Test
    void deleteUserReturnsSuccessMessage() {
        doNothing().when(userService).deleteUser(9L);

        ResponseEntity<String> response = userRestController.deleteUser(9L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
        verify(userService).deleteUser(9L);
    }

    @Test
    void testEndpointReturnsMessage() {
        ResponseEntity<String> response = userRestController.test();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User API + DB working ✅", response.getBody());
    }
}
