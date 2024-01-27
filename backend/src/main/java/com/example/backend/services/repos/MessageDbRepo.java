package com.example.backend.services.repos;

import com.example.backend.models.database.MessageDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDbRepo extends JpaRepository<MessageDbEntity, Long> {
    @Query(value = "SELECT * FROM messages " +
                   "WHERE receiver = :receiver AND " +
                          "sender = :sender " +
                    "ORDER BY date, hour DESC " +
                    "LIMIT 100", nativeQuery = true)
    List<MessageDbEntity> getRecentMessages(@Param("receiver") String receiver,
                                            @Param("sender") String sender);

    @Query(value = "SELECT * FROM messages" +
                    "WHERE receiver = :receiver AND " +
                            "sender = :sender AND " +
                            "message LIKE %:message%", nativeQuery = true)
    List<MessageDbEntity> getMessagesContaining(@Param("receiver") String receiver,
                                                @Param("sender") String sender,
                                                @Param("message") String containingWords);
}
