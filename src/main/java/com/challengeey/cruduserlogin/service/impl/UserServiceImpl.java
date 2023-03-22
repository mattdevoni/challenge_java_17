package com.challengeey.cruduserlogin.service.impl;

import com.challengeey.cruduserlogin.persistence.UserPersistence;
import com.challengeey.cruduserlogin.service.UserService;
import com.challengeey.cruduserlogin.service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserPersistence userPersistence;

    public User createUser(User user) {
        return userPersistence.saveUser(user);
    }

    @Override
    public List<User> listUser() {
        return userPersistence.listUsers();
    }
    @Override
    public String getTokenByEmail(String email) {
        var userFounded = userPersistence.findOneByEmail(email);
        return userFounded.getToken();
    }

    @Override
    public User updateLastLogin(String email) {
        return userPersistence.updateLastLogin(email);
    }
}
