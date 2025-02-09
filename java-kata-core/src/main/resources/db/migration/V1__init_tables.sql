CREATE TABLE `users`
(
    `id`                BIGINT PRIMARY KEY,
    `email`             VARCHAR(255) UNIQUE,
    `password`          VARCHAR(255)           NOT NULL,
    `nickname`          VARCHAR(255)           NOT NULL,
    `role`              ENUM ('USER', 'ADMIN') NOT NULL,
    `provider`          ENUM ('LOCAL', 'GOOGLE', 'GITHUB'),
    `provider_id`       VARCHAR(255),
    `profile_image_url` VARCHAR(200),
    `created_at`        DATETIME DEFAULT NOW(),
    `updated_at`        DATETIME
);

CREATE TABLE `problems`
(
    `id`          BIGINT PRIMARY KEY,
    `title`       VARCHAR(255) NOT NULL,
    `difficulty`  ENUM ('EASY', 'MEDIUM', 'HARD') UNIQUE,
    `description` TEXT         NOT NULL,
    `base_code`   TEXT         NOT NULL,
    `test_code`   TEXT         NOT NULL,
    `solution`    TEXT         NOT NULL,
    `category_id` BIGINT       NOT NULL,
    `created_at`  DATETIME DEFAULT NOW(),
    `updated_at`  DATETIME
);

CREATE TABLE `categories`
(
    `id`          BIGINT PRIMARY KEY,
    `name`        VARCHAR(255) NOT NULL UNIQUE,
    `description` VARCHAR(255) NOT NULL,
    `created_at`  DATETIME DEFAULT NOW(),
    `updated_at`  DATETIME
);

CREATE TABLE `tags`
(
    `id`          BIGINT PRIMARY KEY,
    `name`        VARCHAR(50) NOT NULL UNIQUE,
    `category_id` BIGINT      NOT NULL
);

CREATE TABLE `submission`
(
    `id`             BIGINT PRIMARY KEY,
    `user_id`        BIGINT NOT NULL,
    `problem_id`     BIGINT NOT NULL,
    `status`         ENUM ('PENDING', 'RUNNING', 'SUCCESS', 'FAILED', 'TIME_EXCEED', 'MEMORY_EXCEED', 'COMPILE_ERROR', 'RUNTIME_ERROR', 'INTERNAL_ERROR'),
    `language`       ENUM ('JAVA_21'),
    `code`           TEXT   NOT NULL,
    `result`         TEXT,
    `error`          TEXT,
    `execution_time` INT,
    `created_at`     DATETIME DEFAULT NOW(),
    `updated_at`     DATETIME
);

ALTER TABLE `problems`
    ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `tags`
    ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `submission`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `submission`
    ADD FOREIGN KEY (`problem_id`) REFERENCES `problems` (`id`);
