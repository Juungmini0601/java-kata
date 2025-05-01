package io.javakata.storage.db.core.user;

import io.javakata.model.auth.Role;
import io.javakata.model.user.User;
import io.javakata.storage.db.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author : kimjungmin Created on : 2025. 3. 17.
 */
@ToString
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	public static UserEntity from(User user) {
		return UserEntity.builder()
			.id(user.getId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.password(user.getPassword())
			.role(user.getRole())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.build();
	}

	public User toModel() {
		return User.builder()
			.id(this.getId())
			.email(this.getEmail())
			.nickname(this.getNickname())
			.password(this.getPassword())
			.role(this.getRole())
			.createdAt(this.getCreatedAt())
			.updatedAt(this.getUpdatedAt())
			.build();
	}
}
