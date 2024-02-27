package com.example.backend.api.controllers.message;

import com.example.backend.api.common.RequestCode;
import com.example.backend.models.api.requests.SendMessageRequest;
import com.example.backend.models.api.response.ControllerBasicResponse;
import com.example.backend.models.database.MessageDbEntity;
import com.example.backend.models.database.TextMessageDbEntity;
import com.example.backend.models.database.SentImageDbEntity;
import com.example.backend.services.database.MessageDbService;
import com.example.backend.services.database.SentImageDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/conversation")
public class MessageController {
    private MessageDbService messageDbService;
    private SentImageDbService sentImageDbService;

    @Autowired
    public MessageController(MessageDbService messageDbService, SentImageDbService sentImageDbService) {
        this.messageDbService = messageDbService;
        this.sentImageDbService = sentImageDbService;
    }

    @CrossOrigin
    @PostMapping("/{id}/message/add")
    public ResponseEntity<ControllerBasicResponse> addMessage(@PathVariable Long id,
                                                              @RequestBody SendMessageRequest messageEntity) {
        RequestCode validationMessage = verifyAddRequest(id, messageEntity);

        if(validationMessage != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ControllerBasicResponse("ERROR", validationMessage));
        }

        if(messageEntity.getContentType() == 1) {
            messageDbService.addMessage(new TextMessageDbEntity(id,
                                                            messageEntity.getReceiver(),
                                                            messageEntity.getSender(),
                                                            messageEntity.getContent(),
                                                            messageEntity.getDate(),
                                                            messageEntity.getHour()));
        } else {
            sentImageDbService.addImage(new SentImageDbEntity(id,
                                                                messageEntity.getContent(),
                                                                messageEntity.getReceiver(),
                                                                messageEntity.getSender(),
                                                                messageEntity.getDate(),
                                                                messageEntity.getHour()));
        }

        return  ResponseEntity.status(HttpStatus.OK).body(new ControllerBasicResponse("SUCCESS", RequestCode.OK));
    }

    @CrossOrigin
    @GetMapping("/{id}/message")
    public ResponseEntity<List<? extends MessageDbEntity>> getMessage(@PathVariable Long id) {
        if(!sentImageDbService.conversationExists(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(messageDbService.getRecentMessagesById(id));
    }

    @CrossOrigin
    @PostMapping("/{id}/message/update")
    public ResponseEntity<ControllerBasicResponse> updateMessage(@PathVariable Long id,
                                                                 @RequestBody SendMessageRequest messageEntity) {
        TextMessageDbEntity requestedMessage = messageDbService.getSpecificMessage(messageEntity.getSender(),
                                                                                messageEntity.getReceiver(),
                                                                                messageEntity.getContent(),
                                                                                messageEntity.getDate(),
                                                                                messageEntity.getHour());
        RequestCode validationMessage = verifyUpdateRequest(requestedMessage, messageEntity.getUpdatedContent());

        if(validationMessage != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ControllerBasicResponse("ERROR", validationMessage));
        }

        requestedMessage.setMessage(messageEntity.getUpdatedContent());
        messageDbService.updateMessage(requestedMessage);

        return ResponseEntity.status(HttpStatus.OK).body(new ControllerBasicResponse("SUCCESS", RequestCode.OK));
    }

    @CrossOrigin
    @DeleteMapping("/{id}/message/delete")
    public ResponseEntity<ControllerBasicResponse> deleteMessage(@PathVariable Long id,
                                                                 @RequestBody SendMessageRequest messageEntity) {
        MessageDbEntity requestedMessage = messageEntity.getContentType() == 1 ?
                messageDbService.getSpecificMessage(messageEntity.getSender(),
                                                    messageEntity.getReceiver(),
                                                    messageEntity.getContent(),
                                                    messageEntity.getDate(),
                                                    messageEntity.getHour()) :
                sentImageDbService.getSpecificImage(messageEntity.getSender(),
                                                    messageEntity.getReceiver(),
                                                    messageEntity.getContent(),
                                                    messageEntity.getDate(),
                                                    messageEntity.getHour());

        if(requestedMessage == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ControllerBasicResponse("ERROR", RequestCode.MESSAGE_NOT_FOUND));
        }

        if(messageEntity.getContentType() == 1) {
            messageDbService.deleteMessage((TextMessageDbEntity) requestedMessage);
        } else {
            sentImageDbService.deleteImage((SentImageDbEntity) requestedMessage);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ControllerBasicResponse("SUCCESS", RequestCode.OK));
    }

    private RequestCode verifyAddRequest(Long conversationId, SendMessageRequest messageEntity) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        Integer contentType = messageEntity.getContentType();
        String content = messageEntity.getContent();

        if(!sentImageDbService.conversationExists(conversationId)) {
            return RequestCode.CONVERSATION_NOT_FOUND;
        }

        if(contentType == 1 && (content == null || content.isEmpty())) {
            return RequestCode.EMPTY_MESSAGE;
        }

        if(contentType == 1 && content.length() > 65535) {
            return RequestCode.MESSAGE_TOO_LONG;
        }

        if(dateFormat
                .format(date)
                .compareTo(messageEntity
                        .getDate()
                        .concat(" " + messageEntity.getHour())) > 0) {
            return RequestCode.INVALID_DATE;
        }

        return RequestCode.OK;
    }

    private RequestCode verifyUpdateRequest(TextMessageDbEntity message, String newMessage) {
        if(message == null) {
            return RequestCode.MESSAGE_NOT_FOUND;
        }

        if(newMessage == null || newMessage.isEmpty()) {
            return RequestCode.EMPTY_MESSAGE;
        }

        if(newMessage.length() > 65535) {
            return RequestCode.MESSAGE_TOO_LONG;
        }

        return RequestCode.OK;
    }
}
