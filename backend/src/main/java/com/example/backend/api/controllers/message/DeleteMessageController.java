package com.example.backend.api.controllers.message;

import com.example.backend.api.common.RequestCode;
import com.example.backend.models.api.requests.MessageRequestBody;
import com.example.backend.services.database.MessageDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class DeleteMessageController {
    private MessageDbService messageDbService;

    @Autowired
    public DeleteMessageController(MessageDbService messageDbService) {
        this.messageDbService = messageDbService;
    }

    @CrossOrigin
    @DeleteMapping("/delete")
    public ResponseEntity deleteMessage(@RequestBody MessageRequestBody messageEntity) {
         if(verifyRequest(messageEntity) != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        messageDbService.deleteMessage(messageDbService.getSpecificMessage(messageEntity.getSender(),
                                                                            messageEntity.getReceiver(),
                                                                            messageEntity.getMessage(),
                                                                            messageEntity.getDate(),
                                                                            messageEntity.getHour()));

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

        return RequestCode.OK;
    }
}
