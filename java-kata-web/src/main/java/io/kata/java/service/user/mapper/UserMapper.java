package io.kata.java.service.user.mapper;

import org.jooq.generated.tables.pojos.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.kata.java.service.user.model.SignupUserInfo;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
	Users toEntity(SignupUserInfo signupUserInfo);
}
