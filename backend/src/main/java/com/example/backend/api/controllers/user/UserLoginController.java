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
public class UserLoginController {
    private UserDbService userDbService;

    @Autowired
    public UserLoginController(UserDbService userDbService) {
        this.userDbService = userDbService;
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<RequestCode> login(@RequestBody UserAuthBody user) {
        RequestCode isValid = verifyRequest(user);

        return isValid == RequestCode.OK ?
                ResponseEntity.status(HttpStatus.OK).body(null) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isValid);
    }

    private RequestCode verifyRequest(UserAuthBody user) {
        UserDbEntity requestedUser = userDbService.findUserByUsername(user.getUsername()).get(0);

        if(requestedUser == null) {
            return RequestCode.USER_NOT_FOUND;
        }

        if(!user.getPassword().equals(requestedUser.getPassword())) {
            return RequestCode.WRONG_PASSWORD;
        }

        return RequestCode.OK;
    }
}
