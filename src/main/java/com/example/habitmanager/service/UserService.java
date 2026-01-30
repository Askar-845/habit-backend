
package com.example.habitmanager.service;

import com.example.habitmanager.entity.User;
import com.example.habitmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Suggest habit based on age
    public String suggestHabit(int age) {
        if (age >= 5 && age <= 12) return "Play outdoor games 30 min/day";
        else if (age >= 13 && age <= 18) return "Avoid junk food + exercise";
        else if (age >= 19 && age <= 40) return "Walk 10,000 steps daily";
        else if (age >= 41 && age <= 60) return "Yoga / stretching 20 min/day";
        else return "Light walking + meditation";
    }

    // Create user with auto habit, score, start/end date
    public User createUser(String name, int age) {
        String habit = suggestHabit(age);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(30); // Habit duration = 30 days
        User user = new User(name, age, habit, startDate, endDate);
        return userRepository.save(user);
    }

    // CRUD operations
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUserScore(Long id, int points) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setScore(user.getScore() + points);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public User updateUser(Long id, String name, int age) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    user.setName(name);
    user.setAge(age);

    // Update habit again based on new age
    String habit = suggestHabit(age);
    user.setHabit(habit);

    return userRepository.save(user);
} 
}
