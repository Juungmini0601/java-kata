package io.kata.java.core;

import java.sql.Timestamp;

import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.test.context.ContextConfiguration;

import io.kata.java.core.config.JooqConfig;

/**
 * 멀티 모듈 환경에서 SpringBootApplication이 없어 테스트 깨지는 현상 발생
 * 슬라이스 테스트(JooqTest)는 @SpringBootApplication이 없으면 위와 같은 에러 발생함
 * -> Spring Application Context를 대체 하는 설정 제공
 */
@JooqTest
@ContextConfiguration(classes = {JooqConfig.class})
public class JooqSettingTest {

	@Autowired
	private DSLContext dslContext;

	@Test
	@DisplayName("1) Jooq Setting Test")
	void test() {
		Record1<Timestamp> timeStamp = dslContext.select(DSL.currentTimestamp())
			.from("dual")
			.fetchOne();

		Assertions.assertThat(timeStamp).isNotNull();
	}
}
