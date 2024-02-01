package com.example.backend.services;

import com.example.backend.models.database.MessageDbEntity;
import com.example.backend.repos.MessageDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
        repo.save(message);
    }

    public List<MessageDbEntity> getRecentMessages(String receiver, String sender) {
        return repo.getRecentMessages(receiver, sender);
    }
}
