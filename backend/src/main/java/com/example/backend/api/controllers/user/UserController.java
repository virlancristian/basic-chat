package com.example.backend.api.controllers.user;

import com.example.backend.api.common.RequestCode;
import com.example.backend.models.api.requests.UserAuthBody;
import com.example.backend.models.api.response.ControllerBasicResponse;
import com.example.backend.models.api.response.UserDbResponse;
import com.example.backend.models.database.UserDbEntity;
import com.example.backend.services.database.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserDbService userDbService;

    @Autowired
    public UserController(UserDbService userDbService) {
        this.userDbService = userDbService;
    }

    @CrossOrigin
    @GetMapping("/{username}")
    public ResponseEntity<UserDbResponse> getUser(@PathVariable String username) {
        UserDbEntity user = userDbService.findUserByUsername(username).get(0);

        return ResponseEntity.status(HttpStatus.OK).body(new UserDbResponse(user.getUserID(), user.getUsername()));
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<ControllerBasicResponse> addUser(@RequestBody UserAuthBody userEntity) {
        RequestCode validRequest = verifyAddRequest(userEntity);

        if(validRequest != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ControllerBasicResponse("ERROR", validRequest));
        }

        userDbService.addUser(new UserDbEntity(userEntity.getUsername(), userEntity.getPassword()));

        return ResponseEntity.status(HttpStatus.OK).body(new ControllerBasicResponse("SUCCESS", RequestCode.OK));
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<ControllerBasicResponse> loginUser(@RequestBody UserAuthBody userEntity) {
        RequestCode validRequest = verifyLoginRequest(userEntity);

        if(validRequest != RequestCode.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ControllerBasicResponse("ERROR", validRequest));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ControllerBasicResponse("SUCCESS", RequestCode.OK));
    }

    private RequestCode verifyAddRequest(UserAuthBody userEntity) {
        if(!userDbService.findUserByUsername(userEntity.getUsername()).isEmpty()) {
            return RequestCode.USER_ALREADY_EXISTS;
        }

        return RequestCode.OK;
    }

    private RequestCode verifyLoginRequest(UserAuthBody userEntity) {
        UserDbEntity requestedUser = userDbService.findUserByUsername(userEntity.getUsername()).get(0);

        if(requestedUser == null) {
            return RequestCode.USER_NOT_FOUND;
        }

        if(!requestedUser.getPassword().equals(userEntity.getPassword())) {
            return RequestCode.WRONG_PASSWORD;
        }

        return RequestCode.OK;
    }
}
