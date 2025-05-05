package io.javakata.storage.db.core.submission;

import org.springframework.stereotype.Repository;

import io.javakata.model.submission.Submission;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SubmissionJPAAdaptor implements SubmissionRepository {

    private final SubmissionJPARepository submissionJPARepository;

    public Submission save(Submission submission) {
        SubmissionEntity submissionEntity = SubmissionEntity.from(submission);
        return submissionJPARepository.save(submissionEntity).toModel();
    }

}
