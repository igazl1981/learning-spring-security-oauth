package com.igazl.learning.spring.security.oauthlearning.security;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "jwt.auth.converter")
public class JwtAuthConverterProperties {

    private final String resourceId;
    private final String principalAttribute;

    public JwtAuthConverterProperties(String resourceId, String principalAttribute) {
        this.resourceId = resourceId;
        this.principalAttribute = principalAttribute;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getPrincipalAttribute() {
        return principalAttribute;
    }
}