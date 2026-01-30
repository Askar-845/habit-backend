package com.example.habitmanager.controller;

import com.example.habitmanager.entity.User;
import com.example.habitmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;




@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3002"})
@RestController
@RequestMapping("/api/users")
public class UserRestController {



    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // -----------------------------
    // 1️⃣ Create User (JSON input)
    // -----------------------------
    // -----------------------------
// 6️⃣ Update user name & age
// -----------------------------
@PutMapping("/{id}")
public ResponseEntity<User> updateUser(@PathVariable Long id,
                                       @RequestBody Map<String, Object> payload) {
    String name = (String) payload.get("name");
    int age = (int) payload.get("age");

    User updatedUser = userService.updateUser(id, name, age);
    return ResponseEntity.ok(updatedUser);
}



    
    
    @PostMapping("/create")
public ResponseEntity<User> createUser(@RequestBody Map<String, Object> payload) {
    String name = (String) payload.get("name");
    int age = ((Number) payload.get("age")).intValue();  // 👈 FIXED

    User user = userService.createUser(name, age);
    return ResponseEntity.ok(user);
}


    // -----------------------------
    // 2️⃣ Get all users
    // -----------------------------
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // -----------------------------
    // 3️⃣ Get user by ID
    // -----------------------------
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // -----------------------------
    // 4️⃣ Update user score (mark habit completed)
    // -----------------------------
    @PutMapping("/{id}/score")
    public ResponseEntity<User> updateScore(@PathVariable Long id,
                                            @RequestParam int points) {
        User updated = userService.updateUserScore(id, points);
        return ResponseEntity.ok(updated);
    }

    // -----------------------------
    // 5️⃣ Delete user
    // -----------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
