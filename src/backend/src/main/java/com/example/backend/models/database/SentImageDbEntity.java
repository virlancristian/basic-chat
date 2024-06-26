package com.example.backend.models.database;

import jakarta.persistence.*;

@Entity
@Table(name = "sent_images")
public class SentImageDbEntity extends MessageDbEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "conversation_id")
    private Long conversationId;

    @Column(name = "image_no")
    private Integer imageNumber;
    private String url;
    private String receiver;
    private String sender;
    private String date;
    private String hour;

    public SentImageDbEntity() {
        super(0);
        imageId = 0L;
        conversationId = 0L;
        imageNumber = 0;
        url = "";
        receiver = "";
        sender = "";
        date = "";
        hour = "";
    }

    public SentImageDbEntity(Long conversationId, Integer imageNumber, String url, String receiver, String sender, String date, String hour) {
        super(2);
        this.conversationId = conversationId;
        this.imageNumber = imageNumber;
        this.url = url;
        this.receiver = receiver;
        this.sender = sender;
        this.date = date;
        this.hour = hour;
    }

    public SentImageDbEntity(Long imageId, Long conversationId, Integer imageNumber, String url, String receiver, String sender, String date, String hour) {
        super(2);
        this.imageId = imageId;
        this.conversationId = conversationId;
        this.imageNumber = imageNumber;
        this.url = url;
        this.receiver = receiver;
        this.sender = sender;
        this.date = date;
        this.hour = hour;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Integer getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(Integer imageNumber) {
        this.imageNumber = imageNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        return "SentImageDbEntity{" +
                "imageId=" + imageId +
                ", conversationId=" + conversationId +
                ", url='" + url + '\'' +
                ", receiver='" + receiver + '\'' +
                ", sender='" + sender + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }
}
