package io.javakata.core.auth.application;

/**
 * @author : kimjungmin Created on : 2025. 5. 1.
 */
public interface PasswordEncoder {

    String encode(final String plainText);

    boolean matches(final String plainText, String hashedText);

}
