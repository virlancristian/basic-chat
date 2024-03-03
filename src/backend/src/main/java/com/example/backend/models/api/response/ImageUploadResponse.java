package com.example.backend.models.api.response;

import com.example.backend.api.common.RequestCode;

public class ImageUploadResponse {
    private RequestCode validationMessage;
    private String fileName;
    private Long conversationId;
    private Long imageId;

    public ImageUploadResponse(RequestCode status, String fileName, Long conversationId, Long imageId) {
        this.validationMessage = status;
        this.fileName = fileName;
        this.conversationId = conversationId;
        this.imageId = imageId;
    }

    public RequestCode getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(RequestCode validationMessage) {
        this.validationMessage = validationMessage;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "ImageUploadResponse{" +
                "status='" + validationMessage + '\'' +
                ", fileName='" + fileName + '\'' +
                ", conversationId=" + conversationId +
                ", imageId=" + imageId +
                '}';
    }
}
