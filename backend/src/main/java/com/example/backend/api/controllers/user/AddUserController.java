package com.example.backend.api.controllers.user;

import com.example.backend.api.common.RequestCode;
import com.example.backend.models.api.requests.UserAuthBody;
import com.example.backend.models.database.UserDbEntity;
import com.example.backend.services.database.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AddUserController {
    private UserDbService userDbService;

    @Autowired
    public AddUserController(UserDbService userDbService) {
        this.userDbService = userDbService;
    }

    @CrossOrigin
    @PutMapping("/add")
    public ResponseEntity addUser(@RequestBody UserAuthBody newUser) {
        if(verifyRequest(newUser) != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        userDbService.addUser(new UserDbEntity(newUser.getUsername(), newUser.getPassword()));

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    private RequestCode verifyRequest(UserAuthBody newUser) {
        if(newUser.getUsername() == null) {
            return RequestCode.NULL_USERNAME;
        }

        if(newUser.getPassword() == null) {
            return RequestCode.NULL_PASSWORD;
        }

        if(!userDbService.findUserByUsername(newUser.getUsername()).isEmpty()) {
            return RequestCode.USER_ALREADY_EXISTS;
        }

        return RequestCode.OK;
    }
}
