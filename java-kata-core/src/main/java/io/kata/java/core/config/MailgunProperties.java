package io.kata.java.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

// TODO 내용 블로깅 예정, MailGun Setting 하는 방법도 블로그하자!
@Getter
@ConfigurationProperties(prefix = "mailgun")
public class MailgunProperties {
	private final String apiKey;
	private final String domain;
	private final String from;

	public MailgunProperties(
		String apiKey,
		String domain,
		String from
	) {
		this.apiKey = apiKey;
		this.domain = domain;
		this.from = from;
	}
}


