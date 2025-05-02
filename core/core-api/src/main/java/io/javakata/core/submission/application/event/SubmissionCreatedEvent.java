package io.javakata.core.submission.application.event;

import io.javakata.model.submission.Submission;
import lombok.Getter;

// 이벤트 클래스
@Getter
public class SubmissionCreatedEvent {

    private final Submission submission;

    public SubmissionCreatedEvent(Submission submission) {
        this.submission = submission;
    }

}