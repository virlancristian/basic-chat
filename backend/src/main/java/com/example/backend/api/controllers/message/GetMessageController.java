package com.example.backend.api.controllers.message;

import com.example.backend.models.database.MessageDbEntity;
import com.example.backend.services.database.MessageDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class GetMessageController {
    private MessageDbService messageDbService;

    @Autowired
    public GetMessageController(MessageDbService messageDbService) {
        this.messageDbService = messageDbService;
    }

    @CrossOrigin
    @PostMapping("/get")
    public ResponseEntity<List<MessageDbEntity>> getMessage(@RequestParam Boolean idRequired,
                                                            @RequestParam Boolean allRecipients,
                                                            )
}
