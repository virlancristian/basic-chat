package com.example.backend.repos;

import com.example.backend.models.database.MessageDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDbRepo extends JpaRepository<MessageDbEntity, Long> {
    @Query(value = "SELECT * \n" +
                    "FROM messages\n" +
                    "WHERE (receiver = :user1 AND sender = :user2) OR\n" +
                    "\t  (receiver = :user2 AND sender = :user1)\n" +
                    "ORDER BY date DESC, hour DESC\n" +
                    "LIMIT 100", nativeQuery = true)
    List<MessageDbEntity> getRecentMessages(@Param("user1") String user1,
                                            @Param("user2") String user2);

    @Query(value = "SELECT sender, messageid, date, hour, message, receiver\n" +
                    "FROM (\n" +
                    "\tSELECT *, ROW_NUMBER() OVER (PARTITION BY conversation_id ORDER BY date DESC, hour DESC) AS row_num\n" +
                    "\tFROM messages\n" +
                    "\tWHERE receiver = 'domi' OR sender = 'domi'\n" +
                    ") A\n" +
                    "WHERE row_num = 1\n" +
                    "ORDER BY date DESC, hour DESC\n" +
                    "LIMIT 10;\n", nativeQuery = true)
    List<MessageDbEntity> getUserInbox(@Param("receiver") String receiver);

    @Query(value = "SELECT * FROM messages" +
                    "WHERE receiver = :receiver AND " +
                            "sender = :sender AND " +
                            "message LIKE %:message%", nativeQuery = true)
    List<MessageDbEntity> getMessagesContaining(@Param("receiver") String receiver,
                                                @Param("sender") String sender,
                                                @Param("message") String requiredWords);
}
