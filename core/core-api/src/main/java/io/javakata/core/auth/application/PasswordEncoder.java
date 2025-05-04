package io.javakata.core.auth.application;

public interface PasswordEncoder {

    String encode(final String plainText);

    boolean matches(final String plainText, String hashedText);

}
