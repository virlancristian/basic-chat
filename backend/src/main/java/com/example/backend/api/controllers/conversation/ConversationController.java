package com.example.backend.api.controllers.conversation;

import com.example.backend.api.common.RequestCode;
import com.example.backend.models.api.response.ControllerBasicResponse;
import com.example.backend.models.api.response.UserDbResponse;
import com.example.backend.models.database.ConversationDbEntity;
import com.example.backend.models.database.UserDbEntity;
import com.example.backend.services.database.ConversationDbSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ConversationController {
    private ConversationDbSevice conversationDbSevice;
    private RestTemplate microServiceInvoker;
    private static String USER_MICROSERVICE_API_ENDPOINT;

    @Autowired
    public ConversationController(ConversationDbSevice conversationDbSevice,
                                  @Value("${microservice.user.endpoint}") String userMicroEndpoint) {
        this.conversationDbSevice = conversationDbSevice;
        microServiceInvoker = new RestTemplate();
        USER_MICROSERVICE_API_ENDPOINT = userMicroEndpoint;
    }

    @CrossOrigin
    @GetMapping("/{user}/conversation")
    public ResponseEntity<List<ConversationDbEntity>> getConversation(@PathVariable String user,
                                                                      @RequestParam(value = "recipient", required = false) String recipient) {
        List<ConversationDbEntity> conversations = recipient == null ? conversationDbSevice.getParticipantConversationList(user) : new ArrayList<>();

        if(recipient != null) {
            conversations.add(conversationDbSevice.getConversationByRecipients(user, recipient));
        }

        return ResponseEntity.status(HttpStatus.OK).body(conversations);
    }

    @CrossOrigin
    @PostMapping("/{user}/conversation/add")
    public ResponseEntity<ControllerBasicResponse> addConversation(@PathVariable String user,
                                                                   @RequestParam("recipient") String recipient) {
        RequestCode requestValidationMessage = verifyAddRequest(user, recipient);

        if(requestValidationMessage != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ControllerBasicResponse("ERROR", requestValidationMessage));
        }

        conversationDbSevice.addConversation(new ConversationDbEntity(user, recipient));

        return ResponseEntity.status(HttpStatus.OK).body(new ControllerBasicResponse("SUCCESS", RequestCode.OK));
    }

    @CrossOrigin
    @PostMapping("/{user}/conversation/delete")
    public ResponseEntity<ControllerBasicResponse> deleteConversation(@PathVariable String user,
                                                                      @RequestParam("recipient") String recipient) {
        RequestCode requestValidationMessage = verifyDeleteRequest(user, recipient);

        if(requestValidationMessage != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ControllerBasicResponse("ERROR", requestValidationMessage));
        }

        conversationDbSevice.deleteConversation(conversationDbSevice.getConversationByRecipients(user, recipient));

        return ResponseEntity.status(HttpStatus.OK).body(new ControllerBasicResponse("SUCCESS", RequestCode.OK));
    }

    private RequestCode verifyAddRequest(String user, String recipient) {
        UserDbResponse matchingUser = microServiceInvoker.getForObject(USER_MICROSERVICE_API_ENDPOINT + "/" + user, UserDbResponse.class);
        UserDbResponse matchingRecipient = microServiceInvoker.getForObject(USER_MICROSERVICE_API_ENDPOINT + "/" + recipient, UserDbResponse.class);
        ConversationDbEntity conversation = conversationDbSevice.getConversationByRecipients(user, recipient);

        if(matchingUser == null || matchingRecipient == null) {
            return RequestCode.USER_NOT_FOUND;
        }

        if(conversation != null) {
            return RequestCode.CONVERSATION_ALREADY_EXISTS;
        }

        return RequestCode.OK;
    }

    private RequestCode verifyDeleteRequest(String user, String recipient) {
        if(conversationDbSevice.getConversationByRecipients(user, recipient) == null) {
            return RequestCode.CONVERSATION_NOT_FOUND;
        }

        return RequestCode.OK;
    }
}
