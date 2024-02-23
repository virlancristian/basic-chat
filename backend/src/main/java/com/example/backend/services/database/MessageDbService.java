package com.example.backend.services.database;

import com.example.backend.models.database.MessageDbEntity;
import com.example.backend.repos.MessageDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageDbService {
    private MessageDbRepo repo;

    @Autowired
    public MessageDbService(MessageDbRepo repo) {
        this.repo = repo;
    }

    public void addMessage(MessageDbEntity message) {
        try {
            repo.save(message);
        } catch (IllegalArgumentException | OptimisticLockingFailureException error) {
            System.out.println("Error in MessageDbService::addMessage() - failed to save message "
                                + message.getMessage() + "with recipients"
                                + message.getReceiver() + " " + message.getSender() + ": " + error);
        }
    }

    public void updateMessage(MessageDbEntity message) {
        try {
            repo.save(message);
        } catch (IllegalArgumentException | OptimisticLockingFailureException error) {
            System.out.println("Error in MessageDbService::updateMessage() - failed to save message "
                    + message.getMessage() + "with recipients"
                    + message.getReceiver() + " " + message.getSender() + ": " + error);
        }
    }

    public void deleteMessage(MessageDbEntity message) {
        try {
            repo.delete(message);
        } catch(IllegalArgumentException | OptimisticLockingFailureException error) {
            System.out.println("Error in MessageDbService::deleteMessage() - failed to save message "
                    + message.getMessage() + "with recipients"
                    + message.getReceiver() + " " + message.getSender() + ": " + error);
        }
    }

    public List<MessageDbEntity> getUserInbox(String username) {
        return repo.getUserInbox(username);
    }

    public List<MessageDbEntity> getRecentMessagesById(Long id) {
        return repo.getRecentMessagesById(id);
    }

    public List<MessageDbEntity> getRecentMessagesByRecipients(String user1, String user2) {
        return repo.getRecentMessagesByRecipients(user1, user2);
    }

    public List<MessageDbEntity> getMessagesAfterDateById(Long id, String date) {
        return repo.getMessagesAfterDateById(id, date);
    }

    public List<MessageDbEntity> getMessagesAfterDateByRecipients(String user1, String user2, String date) {
        return repo.getMessagesAfterDateByRecipients(user1, user2, date);
    }

    public MessageDbEntity getMostRecentMessageById(Long id) {
        return repo.getMostRecentMessageById(id);
    }

    public MessageDbEntity getMostRecentMessageByRecipients(String user1, String user2) {
        return repo.getMostRecentMessageByRecipient(user1, user2);
    }

    public MessageDbEntity getSpecificMessage(String sender,
                                              String receiver,
                                              String message,
                                              String date,
                                              String hour) {
        return repo.getSpecificMessage(sender, receiver, message, date, hour);
    }

    public List<MessageDbEntity> getMessageAlikeById(Long id, String message) {
        return repo.getMessageAlikeById(id, message);
    }

    public List<MessageDbEntity> getMessageAlikeByRecipients(String user1, String user2, String message) {
        return repo.getMessageAlikeByRecipients(user1, user2, message);
    }
}
