package com.example.backend.services.database;

import com.example.backend.models.database.ConversationDbEntity;
import com.example.backend.repos.ConversationDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationDbSevice {
    private ConversationDbRepo repo;

    @Autowired
    public ConversationDbSevice(ConversationDbRepo repo) {
        this.repo = repo;
    }

    public ConversationDbEntity getConversationById(Long id) {
        return repo.getConversationById(id);
    }
}
