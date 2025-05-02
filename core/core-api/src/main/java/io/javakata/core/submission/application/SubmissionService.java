package io.javakata.core.submission.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.core.notification.application.NotificationService;
import io.javakata.core.submission.application.event.SubmissionCreatedEvent;
import io.javakata.model.language.Language;
import io.javakata.model.problem.Problem;
import io.javakata.model.submission.Status;
import io.javakata.model.submission.Submission;
import io.javakata.model.submission.result.SubmissionTestCaseResult;
import io.javakata.model.user.User;
import io.javakata.redis.core.service.RedisService;
import io.javakata.storage.db.core.problem.ProblemQuery;
import io.javakata.storage.db.core.submission.SubmissionCommand;
import io.javakata.storage.db.core.user.UserQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionService {

    // TODO 새로운 클래스가 등장해야 될 거 같긴함(너무 크다)

    private final ProblemQuery problemQuery;

    private final UserQuery userQuery;

    private final SubmissionCommand submissionCommand;

    private final ApplicationEventPublisher eventPublisher;

    private final NotificationService notificationService;

    private final RedisService redisService;

    @Transactional
    public Submission submit(Long problemId, Command command, String email) {
        Problem problem = problemQuery.findByIdWithTestCase(problemId);
        User user = userQuery.findByEmailOrElseThrow(email);

        Submission submission = Submission.from(user.getId(), command.language, Status.PENDING, command.code, problem);
        Submission savedSubmission = submissionCommand.save(submission);

        eventPublisher.publishEvent(new SubmissionCreatedEvent(savedSubmission));
        return savedSubmission;
    }

    public void processTestCaseResult(SubmissionTestCaseResult result) {
        if (notificationService.hasEmitter(result.getUserId())) {
            String submitKey = "submission:" + result.getSubmitId() + ":remaining";
            Long remaining = redisService.decrement(submitKey);
            notificationService.send(result.getUserId(), "SUBMIT_RESULT", result);

            // 키 값에 남아 있는 테스트 케이스의 개수가 0이면 채점 완료 메세지 추가로 전송
            if (remaining != null && remaining == 0L) {
                notificationService.send(result.getUserId(), "SUBMIT_COMPLETE", true);
                redisService.delete(submitKey);
            }
        }
    }

    // TODO 클라이언트에서 집계된 데이터를 기반으로 성공 실패 데이터 업데이트 하는 로직 필요

    public record Command(Language language, String code) {
    }

}
