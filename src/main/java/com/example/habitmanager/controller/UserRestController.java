
package com.example.habitmanager.controller;

import com.example.habitmanager.entity.User;
import com.example.habitmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(
        origins = {
                "http://localhost:3000",
                "http://localhost:3002",
                "https://habit-frontend.vercel.app" // frontend deploy irundha
        }
)
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // =============================
    // 1️⃣ Create User
    // =============================
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody Map<String, Object> payload) {

        String name = (String) payload.get("name");
        int age = ((Number) payload.get("age")).intValue();

        User user = userService.createUser(name, age);
        return ResponseEntity.ok(user);
    }

    // =============================
    // 2️⃣ Get All Users
    // =============================
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // =============================
    // 3️⃣ Get User By ID
    // =============================
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =============================
    // 4️⃣ Update User Name & Age
    // =============================
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload
    ) {
        String name = (String) payload.get("name");
        int age = ((Number) payload.get("age")).intValue();

        User updatedUser = userService.updateUser(id, name, age);
        return ResponseEntity.ok(updatedUser);
    }

    // =============================
    // 5️⃣ Update User Score
    // =============================
    @PutMapping("/{id}/score")
    public ResponseEntity<User> updateScore(
            @PathVariable Long id,
            @RequestParam int points
    ) {
        User updated = userService.updateUserScore(id, points);
        return ResponseEntity.ok(updated);
    }

    // =============================
    // 6️⃣ Delete User
    // =============================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    // =============================
    // 7️⃣ Health Check (IMPORTANT)
    // =============================
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("User API + DB working ✅");
    }
}
