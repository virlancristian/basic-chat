package com.example.backend.models.api.response;

public class UserDbResponse {
    private Long userId;
    private String username;

    public UserDbResponse() {}

    public UserDbResponse(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserDbResponse{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
