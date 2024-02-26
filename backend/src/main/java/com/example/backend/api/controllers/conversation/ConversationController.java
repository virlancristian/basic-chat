package com.example.backend.api.controllers.conversation;

import com.example.backend.models.database.ConversationDbEntity;
import com.example.backend.services.database.ConversationDbSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ConversationController {
    private ConversationDbSevice conversationDbSevice;

    @Autowired
    public ConversationController(ConversationDbSevice conversationDbSevice) {
        this.conversationDbSevice = conversationDbSevice;
    }

    @GetMapping("/{user}/conversation")
    public ResponseEntity<List<ConversationDbEntity>> getConversation(@PathVariable String user,
                                                                      @RequestParam(value = "recipient", required = false) String recipient) {
        List<ConversationDbEntity> conversations = recipient == null ? conversationDbSevice.getParticipantConversationList(user) : new ArrayList<>();

        if(recipient != null) {
            conversations.add(conversationDbSevice.getConversationByRecipients(user, recipient));
        }

        return ResponseEntity.status(HttpStatus.OK).body(conversations);
    }
}
