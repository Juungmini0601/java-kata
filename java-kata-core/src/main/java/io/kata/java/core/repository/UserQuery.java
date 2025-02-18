package io.kata.java.core.repository;

import java.util.Optional;

import org.jooq.DSLContext;
import org.jooq.generated.tables.JUsers;
import org.jooq.generated.tables.pojos.Users;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserQuery {

	private final DSLContext dslContext;
	private final JUsers USER = JUsers.USERS;

	@Transactional(readOnly = true)
	public Optional<Users> findOneByEmail(String email) {
		return Optional.ofNullable(
			dslContext.selectFrom(USER)
				.where(USER.EMAIL.eq(email))
				.fetchOneInto(Users.class)
		);
	}
}
