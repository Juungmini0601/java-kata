package io.javakata.storage.db.core.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.model.user.User;
import io.javakata.storage.db.core.error.ConflictException;
import io.javakata.storage.db.core.error.NotFoundException;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserJPAAdaptor implements UserRepository {

    private final UserJPARepository userJPARepository;

    @Transactional
    @Override
    public User save(User user) {
        UserEntity userEntity = UserEntity.from(user);
        return userJPARepository.save(userEntity).toModel();
    }

    @Transactional(readOnly = true)
    @Override
    public void ifExistsByEmailDoThrow(String email) {
        if (userJPARepository.existsByEmail(email)) {
            throw new ConflictException("중복된 이메일: " + email);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User findByEmailOrElseThrow(String email) {
        return userJPARepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("not found user email:" + email))
            .toModel();
    }

}
