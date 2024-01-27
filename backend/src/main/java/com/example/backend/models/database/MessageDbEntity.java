package com.example.backend.models.database;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class MessageDbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageid")
    private Long messageId;
    private String receiver;
    private String sender;
    private String message;
    private String date;
    private String hour;

    public MessageDbEntity() {}

    public MessageDbEntity(String receiver, String sender, String message, String date, String hour) {
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.date = date;
        this.hour = hour;
    }

    public MessageDbEntity(Long messageId, String receiver, String sender, String message, String date, String hour) {
        this.messageId = messageId;
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.date = date;
        this.hour = hour;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
