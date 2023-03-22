package com.challengeey.cruduserlogin.persistence.utils;

import com.challengeey.cruduserlogin.persistence.entity.UserEntity;
import com.challengeey.cruduserlogin.persistence.repository.UserRepository;
import com.challengeey.cruduserlogin.security.TokenUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if(this.userRepository.count() == 0) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            log.info("Attempt of load");
            UserEntity initialUser =
                new UserEntity("johndoe", bCryptPasswordEncoder.encode("pass"), "johndoe@johndoe.cl");

            String newToken = TokenUtils.createToken(initialUser.getName(), initialUser.getEmail());
            initialUser.setToken(newToken);

            UserEntity savedUser = this.userRepository.save(initialUser);
            log.info("user created :{}", savedUser);
        }
    }
}
