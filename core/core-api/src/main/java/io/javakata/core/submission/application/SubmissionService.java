package io.javakata.core.submission.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.core.submission.application.event.SubmissionCreatedEvent;
import io.javakata.model.language.Language;
import io.javakata.model.problem.Problem;
import io.javakata.model.submission.Status;
import io.javakata.model.submission.Submission;
import io.javakata.storage.db.core.problem.ProblemRepository;
import io.javakata.storage.db.core.submission.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final ProblemRepository problemRepository;

    private final SubmissionRepository submissionRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Submission submit(Long problemId, Command command, Long userId) {
        Problem problem = problemRepository.findByIdWithTestCase(problemId);

        Submission submission = Submission.from(userId, command.language, Status.PENDING, command.code, problem);
        Submission savedSubmission = submissionRepository.save(submission);

        eventPublisher.publishEvent(new SubmissionCreatedEvent(savedSubmission));
        return savedSubmission;
    }

    public record Command(Language language, String code) {
    }

}
