package com.example.backend.services.database;

import com.example.backend.models.database.ConversationDbEntity;
import com.example.backend.repos.ConversationDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationDbSevice {
    private ConversationDbRepo repo;

    @Autowired
    public ConversationDbSevice(ConversationDbRepo repo) {
        this.repo = repo;
    }

    public void addConversation(ConversationDbEntity newConversation) {
        try {
            repo.save(newConversation);
        } catch(IllegalArgumentException |
                OptimisticLockingFailureException error) {
            System.out.println("Error in ConversationDbService::addConversation - unable to add conversation with recipients " + newConversation.getFirstParticipant() + ", " + newConversation.getSecondParticipant() + ": " + error);
        }
    }

    public void deleteConversation(ConversationDbEntity conversation) {
        try {
            repo.delete(conversation);
        } catch(IllegalArgumentException |
                OptimisticLockingFailureException error) {
            System.out.println("Error in ConversationDbService::deleteConversation - unable to delete conversation with recipients " + conversation.getFirstParticipant() + ", " + conversation.getSecondParticipant() + ": " + error);
        }
    }

    public ConversationDbEntity getConversationById(Long id) {
        if(id == 0 || id == null) {
            throw new IllegalArgumentException("Id cannot be null or 0!");
        }

        return repo.getConversationById(id);
    }

    public List<ConversationDbEntity> getParticipantConversationList(String participant) {
        if(participant == null) {
            throw new IllegalArgumentException("Participant's username cannot be null!");
        }

        return repo.getParticipantConversationList(participant);
    }

    public ConversationDbEntity getConversationByRecipients(String firstRecipient, String secondRecipient) {
        return repo.getConversationByRecipients(firstRecipient, secondRecipient);
    }
}
