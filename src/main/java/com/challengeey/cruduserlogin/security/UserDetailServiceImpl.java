package com.challengeey.cruduserlogin.security;

import com.challengeey.cruduserlogin.persistence.entity.UserEntity;
import com.challengeey.cruduserlogin.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The user with email " + email + " not exist."));

        return new UserDetailsImpl(userEntity);
    }
}
