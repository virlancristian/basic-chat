package com.example.backend.models.database;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class MessageDbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageid")
    private Long messageId;
    private Long conversationId;
    private String receiver;
    private String sender;
    private String message;
    private String date;
    private String hour;

    public MessageDbEntity() {}

    public MessageDbEntity(Long conversationId, String receiver, String sender, String message, String date, String hour) {
        this.conversationId = conversationId;
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.date = date;
        this.hour = hour;
    }

    public MessageDbEntity(Long messageId, Long conversationId , String receiver, String sender, String message, String date, String hour) {
        this.conversationId = conversationId;
        this.messageId = messageId;
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.date = date;
        this.hour = hour;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
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

    @Override
    public String toString() {
        return "MessageDbEntity{" +
                "messageId=" + messageId +
                ", conversationId=" + conversationId +
                ", receiver='" + receiver + '\'' +
                ", sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }
}
