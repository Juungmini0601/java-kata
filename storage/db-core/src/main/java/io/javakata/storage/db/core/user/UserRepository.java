package io.javakata.storage.db.core.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : kimjungmin Created on : 2025. 5. 1.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    void deleteByEmail(final String email);

}
