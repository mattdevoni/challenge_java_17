package com.challengeey.cruduserlogin.persistence;

import com.challengeey.cruduserlogin.service.model.User;

import java.util.List;

public interface UserPersistence {

    User saveUser (User user);

    List<User> listUsers();

    User updateLastLogin(String email);

    User findOneByEmail(String email);
}
