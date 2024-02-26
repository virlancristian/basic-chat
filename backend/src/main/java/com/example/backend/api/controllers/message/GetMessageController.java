package com.example.backend.api.controllers.message;

import com.example.backend.api.common.RequestCode;
import com.example.backend.models.database.MessageDbEntity;
import com.example.backend.services.database.ConversationDbSevice;
import com.example.backend.services.database.MessageDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversation")
public class GetMessageController {
    private MessageDbService messageDbService;
    private ConversationDbSevice conversationDbSevice;

    @Autowired
    public GetMessageController(MessageDbService messageDbService, ConversationDbSevice conversationDbSevice) {
        this.messageDbService = messageDbService;
        this.conversationDbSevice = conversationDbSevice;
    }

    @CrossOrigin
    @GetMapping("/{id}/messages/get")
    public ResponseEntity<List<MessageDbEntity>> getMessage(@PathVariable Long id) {
        if(verifyRequest(id) != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(messageDbService.getRecentMessagesById(id));
    }

    private RequestCode verifyRequest(Long id) {
        if(id == 0) {
            return RequestCode.INVALID_ID;
        }

        if(conversationDbSevice.getConversationById(id) == null) {
            return RequestCode.CONVERSATION_NOT_FOUND;
        }

        return RequestCode.OK;
    }
}
