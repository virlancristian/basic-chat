package com.example.backend.repos;

import com.example.backend.models.database.SentImageDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SentImageDbRepo extends JpaRepository<SentImageDbEntity, Long> {
    @Query(value = "SELECT COUNT(image_id)\n" +
                    "FROM sent_images\n" +
                    "WHERE conversation_id = :conversationId", nativeQuery = true)
    Long getConversationImageNumber(@Param("conversationId") Long conversationId);
}
