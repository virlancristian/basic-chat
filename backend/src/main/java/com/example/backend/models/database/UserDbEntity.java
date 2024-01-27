package com.example.backend.models.database;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class UserDbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long userID;
    private String username;
    private String password;

    public UserDbEntity() {}

    public UserDbEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDbEntity(Long userid, String username, String password) {
        this.userID = userid;
        this.username = username;
        this.password = password;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDbEntity{" +
                "userid=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
