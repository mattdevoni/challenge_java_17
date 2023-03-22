package com.challengeey.cruduserlogin.persistence.repository;

import com.challengeey.cruduserlogin.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findOneByEmail(String email);
}
