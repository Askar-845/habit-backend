package com.example.habitmanager.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTest {

    @Test
    void noArgsConstructorCreatesEmptyUserWithDefaultScore() {
        User user = new User();

        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getAge());
        assertNull(user.getHabit());
        assertEquals(0, user.getScore());
        assertNull(user.getStartDate());
        assertNull(user.getEndDate());
    }

    @Test
    void allArgsConstructorAndSettersWork() {
        LocalDate start = LocalDate.of(2026, 3, 18);
        LocalDate end = LocalDate.of(2026, 4, 17);
        User user = new User("A", 22, "Walk", start, end);

        assertEquals("A", user.getName());
        assertEquals(22, user.getAge());
        assertEquals("Walk", user.getHabit());
        assertEquals(0, user.getScore());
        assertEquals(start, user.getStartDate());
        assertEquals(end, user.getEndDate());

        user.setName("B");
        user.setAge(23);
        user.setHabit("Yoga");
        user.setScore(10);
        user.setStartDate(start.plusDays(1));
        user.setEndDate(end.plusDays(1));

        assertEquals("B", user.getName());
        assertEquals(23, user.getAge());
        assertEquals("Yoga", user.getHabit());
        assertEquals(10, user.getScore());
        assertEquals(start.plusDays(1), user.getStartDate());
        assertEquals(end.plusDays(1), user.getEndDate());
    }
}
