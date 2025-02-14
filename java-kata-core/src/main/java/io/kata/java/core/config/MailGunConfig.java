package io.kata.java.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;

@EnableConfigurationProperties(MailgunProperties.class)
@Configuration
public class MailGunConfig {

	private final MailgunProperties mailgunProperties;

	public MailGunConfig(MailgunProperties mailgunProperties) {
		this.mailgunProperties = mailgunProperties;
	}

	@Bean
	public MailgunMessagesApi mailgunMessagesApi() {
		return MailgunClient.config(mailgunProperties.getApiKey())
			.createApi(MailgunMessagesApi.class);
	}
}
