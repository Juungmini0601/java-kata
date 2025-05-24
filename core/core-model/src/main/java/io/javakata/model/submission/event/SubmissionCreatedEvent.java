package io.javakata.model.submission.event;

import io.javakata.model.submission.Submission;
import lombok.Getter;

@Getter
public class SubmissionCreatedEvent {

    private final Submission submission;

    public SubmissionCreatedEvent(Submission submission) {
        this.submission = submission;
    }

}