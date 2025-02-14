package io.kata.java.core.mail;

import org.springframework.stereotype.Service;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.model.message.Message;

import io.kata.java.core.config.MailgunProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

	private final MailgunMessagesApi mailgunMessagesApi;
	private final MailgunProperties mailgunProperties;

	// TODO 비동기로 빼는개 좋을 듯
	public void sendEmail(String to, String subject, String content) {
		try {
			Message message = Message.builder()
				.from(String.format("<%s>", mailgunProperties.getFrom()))
				.to(to)
				.subject(subject)
				.text(content)
				.build();

			mailgunMessagesApi.sendMessage(mailgunProperties.getDomain(), message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

}
