package com.example.backend.repos;

import com.example.backend.models.database.ConversationDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationDbRepo extends JpaRepository<ConversationDbEntity, Long> {
    @Query(value = "SELECT * FROM conversations WHERE conversation_id = :id", nativeQuery = true)
    ConversationDbEntity getConversationById(@Param("id") Long conversationId);

    @Query(value = "SELECT * FROM conversations WHERE first_participant = :participant OR second_participant = :participant", nativeQuery = true)
    ConversationDbEntity getConversationByParticipant(@Param("participant") String participant);
}
