
package com.example.habitmanager.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String habit;
    private Integer score = 0;
    private LocalDate startDate;
    private LocalDate endDate;

    public User() {}

    public User(String name, Integer age, String habit, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.age = age;
        this.habit = habit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.score = 0; // Initial score
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getHabit() { return habit; }
    public void setHabit(String habit) { this.habit = habit; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
