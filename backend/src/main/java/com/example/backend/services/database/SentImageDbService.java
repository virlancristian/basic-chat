package com.example.backend.services.database;

import com.example.backend.models.database.SentImageDbEntity;
import com.example.backend.repos.SentImageDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class SentImageDbService {
    private SentImageDbRepo repo;

    @Autowired
    public SentImageDbService(SentImageDbRepo repo) {
        this.repo = repo;
    }

    public void addImage(SentImageDbEntity image) {
        try {
            repo.save(image);
        } catch (IllegalArgumentException | OptimisticLockingFailureException error) {
            System.out.println("Error in SentImageDbService::addImage - unable to save image with url " + image.getUrl() + ": " + error);
        }
    }

    public void deleteImage(SentImageDbEntity image) {
        try {
            repo.delete(image);
        } catch(IllegalArgumentException | OptimisticLockingFailureException error) {
            System.out.println("Error in SentImageDbService::deleteImage - unable to delete image with url " + image.getUrl() + ": " + error);
        }
    }

    public Long getConversationImageNumber(Long conversationId) {
        return repo.getConversationImageNumber(conversationId);
    }

    public boolean conversationExists(Long conversationId) {
        return repo.getConversationById(conversationId) != null;
    }

    public SentImageDbEntity getSpecificImage(String sender,
                                              String receiver,
                                              String url,
                                              String date,
                                              String hour) {
        return repo.getSpecificImage(sender, receiver, url, date, hour);
    }
}
