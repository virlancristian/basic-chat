package com.example.backend.api.controllers.message;

import com.example.backend.api.common.RequestCode;
import com.example.backend.models.api.requests.MessageRequestBody;
import com.example.backend.models.database.MessageDbEntity;
import com.example.backend.services.database.ConversationDbSevice;
import com.example.backend.services.database.MessageDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/message")
public class SendMessageController {
    private MessageDbService messageDbService;
    private ConversationDbSevice conversationDbSevice;

    @Autowired
    public SendMessageController(MessageDbService messageDbService, ConversationDbSevice conversationDbSevice) {
        this.messageDbService = messageDbService;
        this.conversationDbSevice = conversationDbSevice;
    }

    @CrossOrigin
    @PutMapping("/send")
    public ResponseEntity sendMessage(@RequestBody MessageRequestBody messageEntity) {
        Long conversationId;
        System.out.println(verifyRequest(messageEntity));
        if(verifyRequest(messageEntity) != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        conversationId = conversationDbSevice.getConversationByRecipients(messageEntity.getReceiver(), messageEntity.getSender()).getConversationId();
        messageDbService.addMessage(new MessageDbEntity(conversationId,
                                                        messageEntity.getReceiver(),
                                                        messageEntity.getSender(),
                                                        messageEntity.getMessage(),
                                                        messageEntity.getDate(),
                                                        messageEntity.getHour()));

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    private RequestCode verifyRequest(MessageRequestBody messageEntity) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        Date date = new Date();

        if(conversationDbSevice.getConversationByRecipients(messageEntity.getReceiver(), messageEntity.getSender()) == null) {
            return RequestCode.CONVERSATION_NOT_FOUND;
        }

        if(messageEntity.getMessage().length() > 65535) {
            return RequestCode.MESSAGE_TOO_LONG;
        }

        if(dateFormat
                .format(date)
                .toString()
                .compareTo(messageEntity
                        .getDate()
                        .concat(" " + messageEntity.getHour())) > 0) {
            return RequestCode.INVALID_DATE;
        }

        return RequestCode.OK;
    }

}
