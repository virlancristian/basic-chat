package com.example.backend.models.api.requests;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class ImageUploadRequest {
    Long conversationId;
    MultipartFile image;

    public ImageUploadRequest() {}

    public ImageUploadRequest(Long conversationId, MultipartFile image) {
        this.conversationId = conversationId;
        this.image = image;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ImageUploadRequest{" +
                "conversationId=" + conversationId +
                ", image=" + image +
                '}';
    }
}
