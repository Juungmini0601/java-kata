package io.javakata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author : kimjungmin Created on : 2025. 5. 1.
 */
@ConfigurationPropertiesScan
@SpringBootApplication
public class JavaKataApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaKataApplication.class);
    }

}
