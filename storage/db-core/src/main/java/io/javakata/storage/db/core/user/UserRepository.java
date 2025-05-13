package io.javakata.storage.db.core.user;

import io.javakata.model.user.User;

public interface UserRepository {

    User save(User user);

    void ifExistsByEmailDoThrow(final String email);

    User findByEmailOrElseThrow(final String email);

}
