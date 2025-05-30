package io.javakata.storage.db.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = "io.javakata.storage.db.core")
@EnableJpaRepositories(basePackages = "io.javakata.storage.db.core")
public class JpaConfig {

}
