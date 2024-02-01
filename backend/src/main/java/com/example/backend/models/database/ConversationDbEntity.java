package com.example.backend.models.database;

import jakarta.persistence.*;

@Entity
@Table(name = "conversations")
public class ConversationDbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")
    private Long conversationId;

    @Column(name = "first_participant")
    private String firstParticipant;

    @Column(name = "second_participant")
    private String secondParticipant;

    public ConversationDbEntity() {}

    public ConversationDbEntity(String firstParticipant, String secondParticipant) {
        this.firstParticipant = firstParticipant;
        this.secondParticipant = secondParticipant;
    }

    public ConversationDbEntity(Long conversationId, String firstParticipant, String secondParticipant) {
        this.conversationId = conversationId;
        this.firstParticipant = firstParticipant;
        this.secondParticipant = secondParticipant;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
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
        return "ConversationDbEntity{" +
                "conversationId=" + conversationId +
                ", firstParticipant='" + firstParticipant + '\'' +
                ", secondParticipant='" + secondParticipant + '\'' +
                '}';
    }
}
