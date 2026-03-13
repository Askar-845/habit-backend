
package com.example.habitmanager.controller;

import com.example.habitmanager.entity.User;
import com.example.habitmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@CrossOrigin(
    origins = {
        "http://localhost:3000",
        "http://localhost:3002",
        "https://habit-frontend-alpha.vercel.app",
        "https://habit-frontend-ggc4ahbmbxd0bkhr.southeastasia-01.azurewebsites.net"
    },
    methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
    },
    allowedHeaders = "*",
    allowCredentials = "true"
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

        Object nameObj = payload.get("name");
        String name = nameObj == null ? null : String.valueOf(nameObj).trim();
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(BAD_REQUEST, "'name' is required");
        }

        Object ageObj = payload.get("age");
        int age;
        if (ageObj instanceof Number number) {
            age = number.intValue();
        } else if (ageObj instanceof String str) {
            try {
                age = Integer.parseInt(str.trim());
            } catch (NumberFormatException ex) {
                throw new ResponseStatusException(BAD_REQUEST, "'age' must be a number");
            }
        } else {
            throw new ResponseStatusException(BAD_REQUEST, "'age' is required and must be a number");
        }

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
    // 7️⃣ Health Check
    // =============================
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("User API + DB working ✅");
    }
}
