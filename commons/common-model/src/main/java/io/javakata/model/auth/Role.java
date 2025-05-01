package io.javakata.model.auth;

public enum Role {

    ROLE_USER, ROLE_ADMIN;

    public static Role from(String roleName) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("잘못된 Role 값입니다: " + roleName);
    }

}