package com.challengeey.cruduserlogin.controller.mapper;

import com.challengeey.cruduserlogin.controller.dto.PhoneDTO;
import com.challengeey.cruduserlogin.controller.dto.UserDTO;
import com.challengeey.cruduserlogin.controller.dto.UserResponse;
import com.challengeey.cruduserlogin.service.model.Phone;
import com.challengeey.cruduserlogin.service.model.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhones(userDTO.getPhones()
                .stream()
                .map(phoneDTO -> new Phone(phoneDTO.getNumber(), phoneDTO.getCityCode(), phoneDTO.getCountryCode()))
                .collect(Collectors.toList()));
        return user;
    }

    public static UserResponse toUsersResponse(User user) {
        UserResponse usersResponse = new UserResponse();
        usersResponse.setId(user.getId().toString());
        usersResponse.setCreated(user.getCreatedAt());
        usersResponse.setModified(user.getUpdatedAt());
        usersResponse.setLastLogin(user.getLastLogin());
        usersResponse.setToken(user.getToken());
        usersResponse.setActive(user.isActive());
        usersResponse.setPhones(user.getPhones()
                .stream()
                .map(phone -> new PhoneDTO(phone.getNumber(), phone.getCityCode(), phone.getCountryCode()))
                .collect(Collectors.toList()));

        return usersResponse;
    }
}
