package io.javakata.model.user;

/**
 * @author : kimjungmin Created on : 2025. 5. 1.
 */
public record CurrentUser(String email, String role) {

    public static final String Current_USER_KEY = "CURRENT_USER";

}
