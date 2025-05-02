package io.javakata.core.submission.api.request;

import io.javakata.model.language.Language;

// TODO Validation
public record SubmitRequest(Language language, String code) {
}