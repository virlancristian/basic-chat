package com.example.backend.api.controllers.user;

import com.example.backend.models.api.response.UserDbResponse;
import com.example.backend.models.database.UserDbEntity;
import com.example.backend.services.database.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class GetUserController {
    private UserDbService userDbService;

    @Autowired
    public GetUserController(UserDbService userDbService) {
        this.userDbService = userDbService;
    }

    @GetMapping(value = "/get")
    public ResponseEntity<UserDbResponse> getUser(@RequestParam(value = "username", required = false) String username,
                                                  @RequestParam(value = "id", required = false) Long id) {
        UserDbEntity requestedUser = null;

        try {
            requestedUser = id != null ? userDbService.findUserById(id) : userDbService.findUserByUsername(username).get(0);
        } catch(IllegalArgumentException | NullPointerException | IndexOutOfBoundsException error) {
            System.out.println("Error in GetUserController::getUser - unable to get user with " + (id != null ? "id " + id.toString() : "username " + username) + ":\n" + error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new UserDbResponse(requestedUser.getUserID(), requestedUser.getUsername()));
    }
}
