package com.example.backend.models.api.requests;

public class ConversationRequestBody {
    private String firstParticipant;
    private String secondParticipant;

    public ConversationRequestBody() {}

    public ConversationRequestBody(String firstParticipant, String secondParticipant) {
        this.firstParticipant = firstParticipant;
        this.secondParticipant = secondParticipant;
    }

    public String getFirstParticipant() {
        return firstParticipant;
    }

    public void setFirstParticipant(String firstParticipant) {
        this.firstParticipant = firstParticipant;
    }

    public String getSecondParticipant() {
        return secondParticipant;
    }

    public void setSecondParticipant(String secondParticipant) {
        this.secondParticipant = secondParticipant;
    }

    @Override
    public String toString() {
        return "ConversationRequestBody{" +
                "firstParticipant='" + firstParticipant + '\'' +
                ", secondParticipant='" + secondParticipant + '\'' +
                '}';
    }
}
