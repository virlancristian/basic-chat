package com.example.backend.api.controllers.conversation;

import com.example.backend.api.common.RequestCode;
import com.example.backend.models.api.requests.ConversationRequestBody;
import com.example.backend.services.database.ConversationDbSevice;
import com.example.backend.services.database.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversation")
public class DeleteConversationController {
    private ConversationDbSevice conversationDbSevice;
    private UserDbService userDbService;

    @Autowired
    public DeleteConversationController(ConversationDbSevice conversationDbSevice, UserDbService userDbService) {
        this.conversationDbSevice = conversationDbSevice;
        this.userDbService = userDbService;
    }

    @CrossOrigin
    @DeleteMapping("/delete")
    public ResponseEntity<RequestCode> deleteConversation(@RequestBody ConversationRequestBody conversation) {
        RequestCode isValid = verifiyRequest(conversation);

        if(isValid != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isValid);
        }

        conversationDbSevice.deleteConversation(
                conversationDbSevice.getConversationByRecipients(
                        conversation.getFirstParticipant(),
                        conversation.getSecondParticipant()
                )
        );

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    private RequestCode verifiyRequest(ConversationRequestBody conversation) {
        String firstParticipant = conversation.getFirstParticipant();
        String secondParticipant = conversation.getSecondParticipant();

        if(firstParticipant == null || secondParticipant == null) {
            return RequestCode.NULL_USERNAME;
        }

        if(userDbService.findUserByUsername(firstParticipant).isEmpty() ||
                userDbService.findUserByUsername(secondParticipant).isEmpty()) {
            return RequestCode.USER_NOT_FOUND;
        }

        return RequestCode.OK;
    }
}
