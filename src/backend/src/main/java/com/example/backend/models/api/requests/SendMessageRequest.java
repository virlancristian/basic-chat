package com.example.backend.models.api.requests;

public class SendMessageRequest {
    private Integer contentType;        //1 - text message, 2 - image message
    private String message;
    private String sender;
    private String receiver;
    private String date;
    private String hour;
    private String updatedMessage;
    private Integer imageNumber;

    public SendMessageRequest(Integer contentType,
                              String content,
                              String sender,
                              String receiver,
                              String date,
                              String hour,
                              String updatedContent,
                              Integer imageNumber) {
        this.message = content;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.hour = hour;
        this.updatedMessage = updatedContent;
        this.imageNumber = imageNumber;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getUpdatedMessage() {
        return updatedMessage;
    }

    public void setUpdatedMessage(String updatedMessage) {
        this.updatedMessage = updatedMessage;
    }

    public Integer getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(Integer imageNumber) {
        this.imageNumber = imageNumber;
    }

    @Override
    public String toString() {
        return "SendMessageRequest{" +
                "contentType=" + contentType +
                ", content='" + message + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", updatedContent='" + updatedMessage + '\'' +
                '}';
    }
}
