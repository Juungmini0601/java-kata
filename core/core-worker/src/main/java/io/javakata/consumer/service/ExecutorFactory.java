package io.javakata.consumer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.javakata.model.language.Language;

@Component
public class ExecutorFactory {

    private final Map<Language, CodeExecutor> executorMap;

    public ExecutorFactory(List<CodeExecutor> executors) {
        executorMap = new HashMap<>();
        executors.forEach(executor -> executorMap.put(executor.getSupportedLanguage(), executor));
    }

    public CodeExecutor getExecutor(Language language) {
        return executorMap.get(language);
    }

}
