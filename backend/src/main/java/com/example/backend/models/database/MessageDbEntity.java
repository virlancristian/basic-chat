package com.example.backend.models.database;

public class MessageDbEntity {
    private Integer contentType;        //1 - text message, 2 - image message

    public MessageDbEntity(Integer contentType) {
        if(contentType == 1 || contentType == 2) {
            this.contentType = contentType;
        }
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }
}
