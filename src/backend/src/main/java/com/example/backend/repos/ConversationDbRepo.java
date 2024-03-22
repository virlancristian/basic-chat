package com.example.backend.repos;

import com.example.backend.models.database.ConversationDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationDbRepo extends JpaRepository<ConversationDbEntity, Long> {
    @Query(value = "SELECT * FROM conversations WHERE conversation_id = :id", nativeQuery = true)
    ConversationDbEntity getConversationById(@Param("id") Long conversationId);

    @Query(value = "SELECT * FROM conversations WHERE first_participant = :participant OR second_participant = :participant", nativeQuery = true)
    List<ConversationDbEntity> getParticipantConversationList(@Param("participant") String participant);

    @Query(value = "SELECT * FROM conversations WHERE (first_participant = :first_recipient AND second_participant = :second_recipient) OR" +
                    "(first_participant = :second_recipient AND second_participant =:first_recipient)", nativeQuery = true)
    ConversationDbEntity getConversationByRecipients(@Param("first_recipient") String firstRecipient,
                                                     @Param("second_recipient") String secondRecipient);
}
