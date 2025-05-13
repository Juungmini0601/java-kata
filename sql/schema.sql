-- users 테이블
CREATE TABLE `users`
(
    `id`         BIGINT PRIMARY KEY AUTO_INCREMENT,
    `email`      VARCHAR(255)                     NOT NULL UNIQUE,
    `password`   VARCHAR(255)                     NOT NULL,
    `nickname`   VARCHAR(255)                     NOT NULL,
    `role`       ENUM ('ROLE_USER', 'ROLE_ADMIN') NOT NULL,
    `created_at` DATETIME                         NOT NULL,
    `updated_at` DATETIME
);

-- oauths 테이블
CREATE TABLE `oauths`
(
    `id`          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `provider`    ENUM ('GOOGLE', 'GITHUB') NOT NULL,
    `provider_id` VARCHAR(255)              NOT NULL,
    `user_id`     BIGINT                    NOT NULL,
    `created_at`  DATETIME                  NOT NULL,
    `updated_at`  DATETIME,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `uk_provider_providerid` UNIQUE (`provider`, `provider_id`)
);

-- problem_categories 테이블
CREATE TABLE `problem_categories`
(
    `id`            BIGINT PRIMARY KEY AUTO_INCREMENT,
    `category_name` VARCHAR(255) NOT NULL UNIQUE,
    `created_at`    DATETIME     NOT NULL,
    `updated_at`    DATETIME
);

-- problems 테이블
CREATE TABLE `problems`
(
    `id`              BIGINT PRIMARY KEY AUTO_INCREMENT,
    `title`           VARCHAR(255) NOT NULL,
    `level`           ENUM ('EASY', 'MEDIUM', 'HARD'),
    `description`     TEXT         NOT NULL,
    `constraints`     TEXT         NOT NULL,
    `input`           TEXT         NOT NULL,
    `expected_output` TEXT         NOT NULL,
    `base_code`       TEXT         NOT NULL,
    `memory_limit_mb` BIGINT       NOT NULL,
    `time_limit_ms`   BIGINT       NOT NULL,
    `category_id`     BIGINT,
    `created_at`      DATETIME     NOT NULL,
    `updated_at`      DATETIME,
    FOREIGN KEY (`category_id`) REFERENCES `problem_categories` (`id`)
);

-- test_cases 테이블
CREATE TABLE `test_cases`
(
    `id`              BIGINT PRIMARY KEY AUTO_INCREMENT,
    `input`           TEXT     NOT NULL,
    `expected_output` TEXT     NOT NULL,
    `is_public`       BOOLEAN  NOT NULL,
    `problem_id`      BIGINT   NOT NULL,
    `created_at`      DATETIME NOT NULL,
    `updated_at`      DATETIME,
    FOREIGN KEY (`problem_id`) REFERENCES `problems` (`id`)
);

-- submissions 테이블
CREATE TABLE `submissions`
(
    `id`         BIGINT PRIMARY KEY AUTO_INCREMENT,
    `status`     ENUM ('PENDING', 'RUNNING', 'SUCCESS', 'FAILED', 'TIME_EXCEED', 'MEMORY_EXCEED', 'COMPILE_ERROR', 'RUNTIME_ERROR', 'INTERNAL_ERROR'),
    `language`   ENUM ('JAVA_21') NOT NULL,
    `code`       TEXT             NOT NULL,
    `user_id`    BIGINT           NOT NULL,
    `problem_id` BIGINT           NOT NULL,
    `created_at` DATETIME         NOT NULL,
    `updated_at` DATETIME,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    FOREIGN KEY (`problem_id`) REFERENCES `problems` (`id`)
);

-- submission_results 테이블
CREATE TABLE `submission_results`
(
    `id`                BIGINT PRIMARY KEY AUTO_INCREMENT,
    `submission_id`     BIGINT                                                                                                                               NOT NULL,
    `test_case_id`      BIGINT                                                                                                                               NOT NULL,
    `status`            ENUM ('PENDING', 'RUNNING', 'SUCCESS', 'FAILED', 'TIME_EXCEED', 'MEMORY_EXCEED', 'COMPILE_ERROR', 'RUNTIME_ERROR', 'INTERNAL_ERROR') NOT NULL,
    `output`            TEXT,
    `error`             TEXT,
    `execution_time_ms` BIGINT,
    FOREIGN KEY (`submission_id`) REFERENCES `submissions` (`id`),
    FOREIGN KEY (`test_case_id`) REFERENCES `test_cases` (`id`)
);
