package io.javakata.storage.db.core.submission;

import org.springframework.stereotype.Repository;

import io.javakata.model.submission.Submission;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SubmissionCommand {

    private final SubmissionRepository submissionRepository;

    public Submission save(Submission submission) {
        SubmissionEntity submissionEntity = SubmissionEntity.from(submission);
        return submissionRepository.save(submissionEntity).toModel();
    }

}
