package tech.ropaki.ivoting.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private String jwtSecret;

    private Integer tokenValidity;
}
