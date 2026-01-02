package com.example.spilly.models;

import com.google.firebase.firestore.DocumentId;

public class Parent {
    @DocumentId
    private String parentId;
    private String name;
    private String email;
    private String phone;

    // Constructors
    public Parent() {}

    public Parent(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}