package com.example.habitmanager.service;

import com.example.habitmanager.entity.User;
import com.example.habitmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void suggestHabitReturnsExpectedMessageForEachAgeBand() {
        assertEquals("Play outdoor games 30 min/day", userService.suggestHabit(8));
        assertEquals("Avoid junk food + exercise", userService.suggestHabit(16));
        assertEquals("Walk 10,000 steps daily", userService.suggestHabit(30));
        assertEquals("Yoga / stretching 20 min/day", userService.suggestHabit(50));
        assertEquals("Light walking + meditation", userService.suggestHabit(70));
    }

    @Test
    void createUserSetsDatesHabitAndSaves() {
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User created = userService.createUser("Alex", 25);

        assertEquals("Alex", created.getName());
        assertEquals(25, created.getAge());
        assertEquals("Walk 10,000 steps daily", created.getHabit());
        assertEquals(0, created.getScore());
        assertEquals(LocalDate.now(), created.getStartDate());
        assertEquals(LocalDate.now().plusDays(30), created.getEndDate());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUsersDelegatesToRepository() {
        when(userRepository.findAll()).thenReturn(List.of(new User()));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserByIdDelegatesToRepository() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void updateUserScoreAddsPointsAndSaves() {
        User user = new User("Sam", 20, "Walk 10,000 steps daily", LocalDate.now(), LocalDate.now().plusDays(30));
        user.setScore(10);
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updated = userService.updateUserScore(5L, 7);

        assertEquals(17, updated.getScore());
        verify(userRepository, times(1)).findById(5L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUserScoreThrowsWhenUserMissing() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUserScore(99L, 3));
    }

    @Test
    void updateUserUpdatesNameAgeHabitAndSaves() {
        User user = new User("Old", 15, "Avoid junk food + exercise", LocalDate.now(), LocalDate.now().plusDays(30));
        when(userRepository.findById(3L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updated = userService.updateUser(3L, "New", 45);

        assertEquals("New", updated.getName());
        assertEquals(45, updated.getAge());
        assertEquals("Yoga / stretching 20 min/day", updated.getHabit());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUserThrowsWhenUserMissing() {
        when(userRepository.findById(101L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(101L, "A", 10));
    }

    @Test
    void deleteUserDelegatesToRepository() {
        doNothing().when(userRepository).deleteById(4L);

        userService.deleteUser(4L);

        verify(userRepository, times(1)).deleteById(4L);
    }
}
