package com.challengeey.cruduserlogin.persistence.impl;

import com.challengeey.cruduserlogin.persistence.UserPersistence;
import com.challengeey.cruduserlogin.persistence.entity.PhoneEntity;
import com.challengeey.cruduserlogin.persistence.entity.UserEntity;
import com.challengeey.cruduserlogin.persistence.exceptions.PersistenceException;
import com.challengeey.cruduserlogin.persistence.repository.UserRepository;
import com.challengeey.cruduserlogin.security.TokenUtils;
import com.challengeey.cruduserlogin.service.model.Phone;
import com.challengeey.cruduserlogin.service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserPersistenceImpl implements UserPersistence {
    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        var userFounded = userRepository.findOneByEmail(user.getEmail());
        if (userFounded.isPresent()) {
           throw new PersistenceException("FIND_USER_001", "The email is already registered.");
        }

        UserEntity userEntity = mapUserToUserEntities(user);

        // Add token JWT and encode pass
        String newToken = TokenUtils.createToken(user.getName(), user.getEmail());
        userEntity.setToken(newToken);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));

        var userSaved = userRepository.save(userEntity);
        return mapUserEntityToUser(userSaved);
    }

    @Override
    public List<User> listUsers() {
        Iterable<UserEntity> userEntities = userRepository.findAll();
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            users.add(mapUserEntityToUser(userEntity));
        }
        return users;
    }

    @Override
    public User updateLastLogin(String email) {
        var userFounded = userRepository.findOneByEmail(email);
        if (userFounded.isPresent()) {
            var userEntity = userFounded.get();
            userEntity.setLastLogin(LocalDateTime.now());
            var userUpdated = userRepository.save((userEntity));

            return mapUserEntityToUser(userUpdated);
        } else {
            throw new PersistenceException("FIND_USER_002", "There is no user identified with that email.");
        }
    }

    @Override
    @Transactional
    public User findOneByEmail(String email) {
        var userFounded = userRepository.findOneByEmail(email);
        if (userFounded.isPresent()) {
            var userEntity = userFounded.get();
            return mapUserEntityToUser(userEntity);
        } else {
            throw new PersistenceException("FIND_USER_002", "There is no user identified with that email.");
        }
    }

    private UserEntity mapUserToUserEntities(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        userEntity.setToken(user.getToken());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setUpdatedAt(user.getUpdatedAt());
        userEntity.setLastLogin(user.getLastLogin());
        userEntity.setActive(user.isActive());

        List<PhoneEntity> phoneEntities = user.getPhones().stream().map(phone -> {
            PhoneEntity phoneEntity = new PhoneEntity();
            phoneEntity.setNumber(phone.getNumber());
            phoneEntity.setCityCode(phone.getCityCode());
            phoneEntity.setCountryCode(phone.getCountryCode());
            return phoneEntity;
        }).collect(Collectors.toList());

        userEntity.setPhones(phoneEntities);
        return userEntity;
    }

    private User mapUserEntityToUser(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setPassword(userEntity.getPassword());
        user.setEmail(userEntity.getEmail());
        user.setToken(userEntity.getToken());
        user.setCreatedAt(userEntity.getCreatedAt());
        user.setUpdatedAt(userEntity.getUpdatedAt());
        user.setLastLogin(userEntity.getLastLogin());
        user.setActive(userEntity.isActive());

        List<Phone> phones = userEntity.getPhones().stream().map(phoneEntity -> {
            Phone phone = new Phone();
            phone.setNumber(phoneEntity.getNumber());
            phone.setCityCode(phoneEntity.getCityCode());
            phone.setCountryCode(phoneEntity.getCountryCode());
            return phone;
        }).collect(Collectors.toList());

        user.setPhones(phones);
        return user;
    }
}
