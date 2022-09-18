package com.jd2.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt")
@Data
public class JwtSecurityConfig {

    private String secret;
    private Integer expiration;
}
