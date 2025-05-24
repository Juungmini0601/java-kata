package io.javakata.model.submission;

public enum Status {

    PENDING, RUNNING, SUCCESS, FAILED, TIME_EXCEED, MEMORY_EXCEED, COMPILE_ERROR, RUNTIME_ERROR, INTERNAL_ERROR;

    public boolean isFailed() {
        return this == FAILED || this == TIME_EXCEED || this == MEMORY_EXCEED || this == COMPILE_ERROR
                || this == RUNTIME_ERROR || this == INTERNAL_ERROR;
    }

}
