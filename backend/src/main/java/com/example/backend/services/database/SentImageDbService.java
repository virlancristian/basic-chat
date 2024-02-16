package com.example.backend.services.database;

import com.example.backend.repos.SentImageDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SentImageDbService {
    private SentImageDbRepo repo;

    @Autowired
    public SentImageDbService(SentImageDbRepo repo) {
        this.repo = repo;
    }

    public Long getConversationImageNumber(Long conversationId) {
        return repo.getConversationImageNumber(conversationId);
    }
}
