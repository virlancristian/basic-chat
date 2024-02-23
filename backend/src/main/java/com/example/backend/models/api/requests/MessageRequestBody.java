package com.example.backend.models.api.requests;

public class MessageRequestBody {
    private Long conversationId;
    private String sender;
    private String receiver;
    private String message;
    private String date;
    private String hour;
    private String newMessage;

    public MessageRequestBody() {}

    public MessageRequestBody(Long conversationId, String sender, String receiver, String message, String date, String time, String newMessage) {
        this.conversationId = conversationId;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = date;
        this.hour = time;
        this.newMessage = newMessage;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    @Override
    public String toString() {
        return "MessageRequestBody{" +
                "conversationId=" + conversationId +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", newMessage='" + newMessage + '\'' +
                '}';
    }
}
