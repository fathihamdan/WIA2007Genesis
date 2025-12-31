package com.example.spilly.models;

import com.google.firebase.firestore.DocumentId;

public class Teacher {
    @DocumentId
    private String teacherId;
    private String name;
    private String email;
    private String role; // e.g., "Counselor", "Class Teacher", "Principal"

    // Constructors
    public Teacher() {}

    public Teacher(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}