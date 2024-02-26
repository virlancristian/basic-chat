package com.example.backend.api.controllers.conversation;

import com.example.backend.api.common.RequestCode;
import com.example.backend.models.database.ConversationDbEntity;
import com.example.backend.services.database.ConversationDbSevice;
import com.example.backend.services.database.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class GetConversationListController {
    private ConversationDbSevice conversationDbSevice;
    private UserDbService userDbService;

    @Autowired
    public GetConversationListController(ConversationDbSevice conversationDbSevice, UserDbService userDbService) {
        this.conversationDbSevice = conversationDbSevice;
        this.userDbService = userDbService;
    }

    @CrossOrigin
    @GetMapping("/{username}/conversations/get")
    public ResponseEntity<List<ConversationDbEntity>> getConversationList(@PathVariable String username) {
        if(verifyRequest(username) != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(conversationDbSevice.getParticipantConversationList(username));
    }

    private RequestCode verifyRequest(String username) {
        return !userDbService.findUserByUsername(username).isEmpty() ? RequestCode.OK : RequestCode.USER_NOT_FOUND;
    }
}
