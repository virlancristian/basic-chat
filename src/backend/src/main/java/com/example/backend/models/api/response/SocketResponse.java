package com.example.backend.models.api.response;

public class SocketResponse {
    private Long updatedMessageID;
    private String updateType;  //edited, deleted
    private String newMessage; //optional

    public SocketResponse(Long updatedMessageID, String updateType) {
        this.updatedMessageID = updatedMessageID;

        if(updateType.equals("edited") || updateType.equals("deleted") || updateType.equals("failed")) {
            this.updateType = updateType;
        }
    }

    public SocketResponse(Long updatedMessageID, String updateType, String newMessages) {
        this.updatedMessageID = updatedMessageID;
        this.updateType = updateType;
        this.newMessage = newMessages;
    }

    public Long getUpdatedMessageID() {
        return updatedMessageID;
    }

    public void setUpdatedMessageID(Long updatedMessageID) {
        this.updatedMessageID = updatedMessageID;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    @Override
    public String toString() {
        return "SocketResponse{" +
                "updatedMessageID=" + updatedMessageID +
                ", updateType='" + updateType + '\'' +
                '}';
    }
}
