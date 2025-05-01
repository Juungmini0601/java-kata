package io.javakata.storage.db.core.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.model.user.User;
import lombok.RequiredArgsConstructor;

/**
 * @author : kimjungmin Created on : 2025. 5. 1.
 */
@Repository
@RequiredArgsConstructor
public class UserCommand {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        UserEntity userEntity = UserEntity.from(user);
        return userRepository.save(userEntity).toModel();
    }

    @Transactional
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

}
