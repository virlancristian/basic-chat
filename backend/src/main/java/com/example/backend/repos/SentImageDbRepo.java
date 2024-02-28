package com.example.backend.repos;

import com.example.backend.models.database.SentImageDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SentImageDbRepo extends JpaRepository<SentImageDbEntity, Long> {
    @Query(value = "SELECT COUNT(image_id)\n" +
                    "FROM sent_images\n" +
                    "WHERE conversation_id = :conversationId", nativeQuery = true)
    Long getConversationImageNumber(@Param("conversationId") Long conversationId);

    @Query(value = "SELECT B.conversation_id\n" +
                    "FROM sent_images A\n" +
                    "RIGHT JOIN conversations B ON A.conversation_id = B.conversation_id\n" +
                    "WHERE B.conversation_id = :id", nativeQuery = true)
    Long getConversationById(@Param("id") Long id);

    @Query(value = "SELECT * \n" +
                    "FROM sent_images\n" +
                    "WHERE sender = :sender AND\n" +
                    "\t\treceiver = :receiver AND\n" +
                    "        url = :url AND\n" +
                    "        date = :date AND\n" +
                    "        hour = :hour", nativeQuery = true)
    SentImageDbEntity getSpecificImage(@Param("sender") String sender,
                                       @Param("receiver") String receiver,
                                       @Param("url") String url,
                                       @Param("date") String date,
                                       @Param("hour") String hour);

    @Query(value = "SELECT * \n" +
                    "FROM sent_images\n" +
                    "WHERE conversation_id = :id\n" +
                    "ORDER BY date DESC, hour DESC;", nativeQuery = true)
    List<SentImageDbEntity> getRecentImagesById(@Param("id") Long id);
}
