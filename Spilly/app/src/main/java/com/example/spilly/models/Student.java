package com.example.spilly.models;

import com.google.firebase.firestore.DocumentId;

public class Student {
    @DocumentId
    private String studentId;
    private String parentId; // FK to Parent
    private String name;
    private String email;
    private String className;

    // Constructors
    public Student() {}

    public Student(String parentId, String name, String email, String className) {
        this.parentId = parentId;
        this.name = name;
        this.email = email;
        this.className = className;
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}