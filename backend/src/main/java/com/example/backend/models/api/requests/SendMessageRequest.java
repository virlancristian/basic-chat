package com.example.backend.models.api.requests;

public class SendMessageRequest {
    private Integer contentType;        //1 - text message, 2 - image message
    private String content;
    private String sender;
    private String receiver;
    private String date;
    private String hour;
    private String updatedContent;

    public SendMessageRequest(Integer contentType,
                              String content,
                              String sender,
                              String receiver,
                              String date,
                              String hour,
                              String updatedContent) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.hour = hour;
        this.updatedContent = updatedContent;

        if(contentType == 1 || contentType == 2) {
            this.contentType = contentType;
        }
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        if(contentType == 1 || contentType == 2) {
            this.contentType = contentType;
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getUpdatedContent() {
        return updatedContent;
    }

    public void setUpdatedContent(String updatedContent) {
        this.updatedContent = updatedContent;
    }

    @Override
    public String toString() {
        return "SendMessageRequest{" +
                "contentType=" + contentType +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", updatedContent='" + updatedContent + '\'' +
                '}';
    }
}
