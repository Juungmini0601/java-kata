package io.javakata.storage.db.core.submission;

import io.javakata.model.submission.Submission;

public interface SubmissionRepository {

    Submission save(Submission submission);

}
