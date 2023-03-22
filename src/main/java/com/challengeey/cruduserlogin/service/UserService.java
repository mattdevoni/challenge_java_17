package com.challengeey.cruduserlogin.service;

import com.challengeey.cruduserlogin.service.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    List<User> listUser();

    User updateLastLogin(String email);

    String getTokenByEmail(String email);
}
