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
import java.util.ArrayList;
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
                                                            messageEntity.getMessage(),
                                                            messageEntity.getDate(),
                                                            messageEntity.getHour()));
        } else {
            sentImageDbService.addImage(new SentImageDbEntity(id,
                                                                messageEntity.getMessage(),
                                                                messageEntity.getReceiver(),
                                                                messageEntity.getSender(),
                                                                messageEntity.getDate(),
                                                                messageEntity.getHour()));
        }

        return  ResponseEntity.status(HttpStatus.OK).body(new ControllerBasicResponse("SUCCESS", RequestCode.OK));
    }

    @CrossOrigin
    @GetMapping("/{id}/message")
    public ResponseEntity<List<? extends MessageDbEntity>> getMessage(@PathVariable Long id,
                                                                      @RequestParam(value = "timestamp", required = false) String timestamp,
                                                                      @RequestParam(value = "type", required = false) String type,
                                                                      @RequestParam(value = "like", required = false) String alikeMessage) {
        if(verifyGetRequest(id, timestamp, type) != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }

        if(timestamp != null && !timestamp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
              mergeMessages(messageDbService.getMessagesAfterDateById(id, timestamp),
                            sentImageDbService.getImagesAfterDateById(id, timestamp))
            );
        }

        if(type != null && !type.isEmpty()) {
            if(type.equals("inbox")) {
                return ResponseEntity.status(HttpStatus.OK).body(getInbox(id));
            }

            return ResponseEntity.status(HttpStatus.OK).body(getSpecificMessage(id, alikeMessage));
        }

        return ResponseEntity.status(HttpStatus.OK).body(mergeMessages(
                messageDbService.getRecentMessagesById(id),
                sentImageDbService.getRecentMessagesById(id)
        ));
    }

    @CrossOrigin
    @PostMapping("/{id}/message/update")
    public ResponseEntity<ControllerBasicResponse> updateMessage(@PathVariable Long id,
                                                                 @RequestBody SendMessageRequest messageEntity) {
        TextMessageDbEntity requestedMessage = messageDbService.getSpecificMessage(messageEntity.getSender(),
                                                                                messageEntity.getReceiver(),
                                                                                messageEntity.getMessage(),
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
                                                    messageEntity.getMessage(),
                                                    messageEntity.getDate(),
                                                    messageEntity.getHour()) :
                sentImageDbService.getSpecificImage(messageEntity.getSender(),
                                                    messageEntity.getReceiver(),
                                                    messageEntity.getMessage(),
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date();
        Integer contentType = messageEntity.getContentType();
        String content = messageEntity.getMessage();

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
                        .concat(" " + messageEntity.getHour())) < 0) {
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

    private RequestCode verifyGetRequest(Long id, String timestamp, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date();

        if(!sentImageDbService.conversationExists(id)) {
            return RequestCode.CONVERSATION_NOT_FOUND;
        }

        if((timestamp != null && !timestamp.isEmpty())
                && dateFormat.format(date).compareTo(timestamp) < 0) {
            return RequestCode.INVALID_DATE;
        }

        if((type != null && !type.isEmpty())
                && !type.equals("inbox") && !type.equals("specific")) {
            return RequestCode.INVALID_MESSAGE_TYPE;
        }

        return RequestCode.OK;
    }

    private List<MessageDbEntity> mergeMessages(List<TextMessageDbEntity> textMessages,
                                                List<SentImageDbEntity> images) {
        List<MessageDbEntity> messages = new ArrayList<>();
        TextMessageDbEntity[] textMessagesArray = new TextMessageDbEntity[textMessages.size()];
        SentImageDbEntity[] imagesArray = new SentImageDbEntity[images.size()];
        int i = 0, j = 0;
        int m = textMessages.size();
        int n = images.size();

        textMessagesArray = textMessages.toArray(textMessagesArray);
        imagesArray = images.toArray(imagesArray);

        while(i < m || j < n) {
            if(i == m) {
                while(j < n) {
                    imagesArray[j].setContentType(2);
                    messages.add(imagesArray[j]);
                    j++;
                }

                break;
            }

            if(j == n) {
                while(i < m) {
                    textMessagesArray[i].setContentType(1);
                    messages.add(textMessagesArray[i]);
                    i++;
                }

                break;
            }

            if(textMessagesArray[i]
                    .getDate()
                    .concat(" " + textMessagesArray[i].getHour())
                    .compareTo(imagesArray[j].getDate()
                            .concat(" " + imagesArray[j].getHour())) > 0) {
                textMessagesArray[i].setContentType(1);
                messages.add(textMessagesArray[i]);
                i++;
            } else {
                imagesArray[j].setContentType(2);
                messages.add(imagesArray[j]);
                j++;
            }

        }

        return messages;
    }

    private List<MessageDbEntity> getInbox(Long id) {
        TextMessageDbEntity recentTextMessage = messageDbService.getMostRecentMessageById(id);
        SentImageDbEntity recentImageMessage = sentImageDbService.getMostRecentMessageById(id);

        if((recentTextMessage == null && recentImageMessage == null)
                || (recentTextMessage.getMessageId() == 0 && recentImageMessage.getImageId() == 0)) {
            return List.of(new TextMessageDbEntity());
        }

        if(recentTextMessage
                .getDate()
                .concat(" " + recentTextMessage.getHour())
                .compareTo(recentImageMessage
                                            .getDate()
                                            .concat(" " + recentImageMessage.getHour())) > 0) {
            recentTextMessage.setContentType(1);

            return List.of(recentTextMessage);
        }

        recentImageMessage.setContentType(2);
        return List.of(recentImageMessage);
    }

    private List<? extends MessageDbEntity> getSpecificMessage(Long conversationId, String alikeMessage) {
        if(alikeMessage == null || alikeMessage.isEmpty()) {
            return new ArrayList<>();
        }

        return messageDbService.getMessageAlikeById(conversationId, "%".concat(alikeMessage).concat("%"));
    }
}
