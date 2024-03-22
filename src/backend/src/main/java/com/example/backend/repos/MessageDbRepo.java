package com.example.backend.repos;

import com.example.backend.models.database.TextMessageDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDbRepo extends JpaRepository<TextMessageDbEntity, Long> {
    @Query(value = "SELECT * \n" +
                    "FROM (\n" +
                    "\tSELECT *, ROW_NUMBER() OVER (PARTITION BY conversationId ORDER BY date DESC, hour DESC) AS row_num\n" +
                    "    FROM messages\n" +
                    ") A\n" +
                    "WHERE row_num = 1 AND (sender = :recipient OR receiver = :recipient)\n" +
                    "ORDER BY date DESC, hour DESC\n" +
                    "LIMIT 10;", nativeQuery = true)
    List<TextMessageDbEntity> getUserInbox(@Param("recipient") String recipient);

    @Query(value = "SELECT *\n" +
                    "FROM messages\n" +
                    "WHERE conversationId = :conversation_id\n" +
                    "ORDER BY date DESC, HOUR desc\n" +
                    "LIMIT 100;", nativeQuery = true)
    List<TextMessageDbEntity> getRecentMessagesById(@Param("conversation_id") Long id);

    @Query(value = "SELECT * \n" +
                    "FROM messages\n" +
                    "WHERE (receiver = :user1 AND sender = :user2) OR (receiver = :user2 AND sender = :user1)\n" +
                    "ORDER BY date DESC, hour DESC\n" +
                    "LIMIT 100;", nativeQuery = true)
    List<TextMessageDbEntity> getRecentMessagesByRecipients(@Param("user1") String user1,
                                                            @Param("user2") String user2);
    @Query(value = "SELECT *\n" +
                    "FROM messages\n" +
                    "WHERE conversationId = :conversation_id AND TIMESTAMP(CONCAT(date, ' ',hour)) < :date\n" +
                    "ORDER BY date DESC, hour DESC\n" +
                    "LIMIT 100;", nativeQuery = true)
    List<TextMessageDbEntity> getMessagesAfterDateById(@Param("conversation_id") Long conversationId,
                                                       @Param("date") String date);

    @Query(value = "SELECT * \n" +
                    "FROM messages\n" +
                    "WHERE ((sender = :user1 AND receiver = :user2) OR (sender = :user2 AND receiver = :user1))\n" +
                    "\t\tAND TIMESTAMP(CONCAT(date, ' ',hour)) < :date\n" +
                    "ORDER BY date DESC, hour DESC\n" +
                    "LIMIT 100;", nativeQuery = true)
    List<TextMessageDbEntity> getMessagesAfterDateByRecipients(@Param("user1") String user1,
                                                               @Param("user2") String user2,
                                                               @Param("date") String date);

    @Query(value = "SELECT * \n" +
                    "FROM messages\n" +
                    "WHERE conversationId = :conversation_id\n" +
                    "ORDER BY date DESC, hour DESC\n" +
                    "LIMIT 1;", nativeQuery = true)
    TextMessageDbEntity getMostRecentMessageById(@Param("conversation_id") Long conversationId);

    @Query(value = "SELECT *\n" +
                    "FROM messages\n" +
                    "WHERE (sender = :user1 AND receiver = :user2) OR (sender = :user2 AND receiver = :user1)\n" +
                    "ORDER BY date DESC, hour DESC\n" +
                    "LIMIT 1;", nativeQuery = true)
    TextMessageDbEntity getMostRecentMessageByRecipient(@Param("user1") String user1,
                                                        @Param("user2") String user2);

    @Query(value = "SELECT * \n" +
                    "FROM messages\n" +
                    "WHERE sender = :sender AND\n" +
                    "\t  receiver = :receiver AND\n" +
                    "      message = :message AND\n" +
                    "      date = :date AND\n" +
                    "      hour = :hour", nativeQuery = true)
    TextMessageDbEntity getSpecificMessage(@Param("sender") String sender,
                                           @Param("receiver") String receiver,
                                           @Param("message") String message,
                                           @Param("date") String date,
                                           @Param("hour") String hour);

    @Query(value = "SELECT * \n" +
                    "FROM messages\n" +
                    "WHERE conversationId = :conversation_id AND\n" +
                    "\t\tmessage LIKE :message\n" +
                    "ORDER BY date DESC, hour DESC;", nativeQuery = true)
    List<TextMessageDbEntity> getMessageAlikeById(@Param("conversation_id") Long conversationId,
                                                  @Param("message") String message);

    @Query(value = "SELECT * \n" +
                    "FROM messages\n" +
                    "WHERE ((sender = :user1 AND receiver = :user2) OR (sender = :user2 AND receiver = :user1)) AND\n" +
                    "\t\tmessage LIKE :message\n" +
                    "ORDER BY date DESC, hour DESC;", nativeQuery = true)
    List<TextMessageDbEntity> getMessageAlikeByRecipients(@Param("user1") String user1,
                                                          @Param("user2") String user2,
                                                          @Param("message") String message);
}
