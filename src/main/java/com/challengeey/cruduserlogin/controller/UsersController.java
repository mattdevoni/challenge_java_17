package com.challengeey.cruduserlogin.controller;

import com.challengeey.cruduserlogin.controller.dto.UserDTO;
import com.challengeey.cruduserlogin.controller.dto.UserResponse;
import com.challengeey.cruduserlogin.exceptions.EmailException;
import com.challengeey.cruduserlogin.exceptions.PasswordException;
import com.challengeey.cruduserlogin.controller.mapper.UserMapper;
import com.challengeey.cruduserlogin.controller.utils.Validators;
import com.challengeey.cruduserlogin.service.UserService;
import com.challengeey.cruduserlogin.service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserDTO user) {
        if(!Validators.validateEmail(user.getEmail())) {
            throw new EmailException();
        }

        if(!Validators.validatePassword(user.getPassword())) {
            throw new PasswordException();
        }

        User userCreated = userService.createUser(UserMapper.toUser(user));
        UserResponse userResponse = UserMapper.toUsersResponse(userCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listUsers() {
        List<User> users = userService.listUser();
        List<UserResponse> usersResponse = new ArrayList<>();
        for (User user : users) {
            usersResponse.add(UserMapper.toUsersResponse(user));
        }

        return ResponseEntity.status(HttpStatus.OK).body(usersResponse);
    }
}
