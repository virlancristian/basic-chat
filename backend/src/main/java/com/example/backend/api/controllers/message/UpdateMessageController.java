package com.example.backend.api.controllers.message;

import com.example.backend.api.common.RequestCode;
import com.example.backend.models.api.requests.MessageRequestBody;
import com.example.backend.models.database.MessageDbEntity;
import com.example.backend.services.database.MessageDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class UpdateMessageController {
    private MessageDbService messageDbService;

    @Autowired
    public UpdateMessageController(MessageDbService messageDbService) {
        this.messageDbService = messageDbService;
    }

    @CrossOrigin
    @PostMapping("/update")
    public ResponseEntity updateMessage(@RequestBody MessageRequestBody messageEntity) {
        MessageDbEntity message;

        if(verifyRequest(messageEntity) != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        message = messageDbService.getSpecificMessage(messageEntity.getSender(),
                                                                messageEntity.getReceiver(),
                                                                messageEntity.getMessage(),
                                                                messageEntity.getDate(),
                                                                messageEntity.getHour());
        message.setMessage(messageEntity.getNewMessage());
        messageDbService.updateMessage(message);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    private RequestCode verifyRequest(MessageRequestBody messageEntity) {
        if(messageDbService.getSpecificMessage(messageEntity.getSender(),
                messageEntity.getReceiver(),
                messageEntity.getMessage(),
                messageEntity.getDate(),
                messageEntity.getHour()) == null) {
            return RequestCode.MESSAGE_NOT_FOUND;
        }

        if(messageEntity.getNewMessage().length() > 65535) {
            return RequestCode.MESSAGE_TOO_LONG;
        }

        return RequestCode.OK;
    }
}
