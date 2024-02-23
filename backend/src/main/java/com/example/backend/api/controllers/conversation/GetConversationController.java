package com.example.backend.api.controllers.conversation;

import com.example.backend.api.common.RequestCode;
import com.example.backend.models.api.requests.ConversationRequestBody;
import com.example.backend.models.database.ConversationDbEntity;
import com.example.backend.services.database.ConversationDbSevice;
import com.example.backend.services.database.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversation")
public class GetConversationController {
    private ConversationDbSevice conversationDbSevice;
    private UserDbService userDbService;

    @Autowired
    public GetConversationController(ConversationDbSevice conversationDbSevice, UserDbService userDbService) {
        this.conversationDbSevice = conversationDbSevice;
        this.userDbService = userDbService;
    }

    @CrossOrigin
    @GetMapping("/get")
    public ResponseEntity<ConversationDbEntity> getConversation(@RequestParam(value="idRequired", required = false) boolean idRequired,
                                                                @RequestParam(value = "id", required = false) Long id,
                                                                @RequestParam(value = "first_recipient", required = false) String firstRecipient,
                                                                @RequestParam(value = "second_recipient", required = false) String secondRecipient) {
        RequestCode isValid = idRequired ? verifyId(id) : verifyParticipants(firstRecipient, secondRecipient);
        ConversationDbEntity requiredConversation = null;

        if(isValid != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        requiredConversation = idRequired ? conversationDbSevice.getConversationById(id) : conversationDbSevice.getConversationByRecipients(firstRecipient, secondRecipient);

        return requiredConversation != null ?
                ResponseEntity.status(HttpStatus.OK).body(requiredConversation) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    private RequestCode verifyId(Long id) {
        return id != null && id != 0 ? RequestCode.OK : RequestCode.INVALID_ID;
    }

    private RequestCode verifyParticipants(String firstParticipant, String secondParticipant) {
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
