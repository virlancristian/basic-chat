package com.example.backend.api.controllers.conversation;

import com.example.backend.api.common.RequestCode;
import com.example.backend.models.api.requests.ConversationRequestBody;
import com.example.backend.models.database.ConversationDbEntity;
import com.example.backend.services.database.ConversationDbSevice;
import com.example.backend.services.database.UserDbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversation")
public class GetConversationController {
    private ConversationDbSevice conversationDbSevice;
    private UserDbService userDbService;

    @CrossOrigin
    @GetMapping("/get")
    public ResponseEntity<ConversationDbEntity> getConversation(@RequestParam(value="idRequired", required = false) boolean idRequired,
                                                       @RequestBody(required = false) Long id,
                                                       @RequestBody ConversationRequestBody conversation) {
        RequestCode isValid = idRequired ? verifyId(id) : verifyParticipants(conversation);
        ConversationDbEntity requiredConversation = null;

        if(isValid != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        requiredConversation = idRequired ? conversationDbSevice.getConversationById(id) : conversationDbSevice.getConversationByRecipients(conversation.getFirstParticipant(), conversation.getSecondParticipant());

        return requiredConversation != null ?
                ResponseEntity.status(HttpStatus.OK).body(requiredConversation) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    private RequestCode verifyId(Long id) {
        return id != 0 ? RequestCode.OK : RequestCode.INVALID_ID;
    }

    private RequestCode verifyParticipants(ConversationRequestBody conversation) {
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
