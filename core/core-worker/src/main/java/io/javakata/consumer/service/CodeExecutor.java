package io.javakata.consumer.service;

import io.javakata.model.language.Language;
import io.javakata.model.submission.EvaluationRequest;

public interface CodeExecutor {

    void execute(EvaluationRequest evaluationRequest);

    Language getSupportedLanguage();

}
