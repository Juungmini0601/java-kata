package io.javakata.storage.db.core.error;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }

}
