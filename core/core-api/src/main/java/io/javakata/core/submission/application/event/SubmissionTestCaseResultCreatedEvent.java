package io.javakata.core.submission.application.event;

import io.javakata.model.submission.result.SubmissionTestCaseResult;
import lombok.Getter;

// 이벤트 클래스
@Getter
public class SubmissionTestCaseResultCreatedEvent {

    private final SubmissionTestCaseResult submissionTestCaseResult;

    public SubmissionTestCaseResultCreatedEvent(SubmissionTestCaseResult submissionTestCaseResult) {
        this.submissionTestCaseResult = submissionTestCaseResult;
    }

}