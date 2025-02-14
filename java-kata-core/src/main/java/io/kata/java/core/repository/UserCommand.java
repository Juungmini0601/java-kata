package io.kata.java.core.repository;

import org.jooq.DSLContext;
import org.jooq.generated.tables.JUsers;
import org.jooq.generated.tables.pojos.Users;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserCommand {

	private final DSLContext dslContext;
	private final JUsers USER = JUsers.USERS;

	@Transactional
	public Long save(Users user) {
		return dslContext.insertInto(USER,
				USER.EMAIL,
				USER.PASSWORD,
				USER.NICKNAME
			)
			.values(user.getEmail(), user.getPassword(), user.getNickname())
			.returningResult(USER.ID)
			.fetchOneInto(Long.class);
	}
}
