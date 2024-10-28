package com.usermodule;

public class LoginResponse {
    private String message;
    private User user;  // You can customize the fields from the User class that you want to return

    // Constructors
    public LoginResponse(String message, User user) {
        this.message = message;
        this.user = user;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
