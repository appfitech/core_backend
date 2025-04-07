package com.fitech.app.users.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "fitness_goal_type")
public class FitnessGoalType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FitnessGoalType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public FitnessGoalType() {
    }
} 