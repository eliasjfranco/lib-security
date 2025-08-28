package com.mock.security.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Validated
@ConfigurationProperties(prefix = "security")
public class JwtAuthProperties {

    private String secret;
    @NotBlank
    private String rolesClaim = "roles";
    private String rolePrefix = "ROLE_";
    private long expirationMillis = 3600000;

    private List<String> publicPaths;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRolePrefix() {
        return rolePrefix;
    }

    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    public List<String> getPublicPaths() {
        return publicPaths;
    }

    public void setPublicPaths(List<String> publicPaths) {
        this.publicPaths = publicPaths;
    }

    public String getRolesClaim() {
        return rolesClaim;
    }

    public void setRolesClaim(String rolesClaim) {
        this.rolesClaim = rolesClaim;
    }

    public long getExpirationMillis() { return expirationMillis; }

    public void setExpirationMillis(long expirationMillis) { this.expirationMillis = expirationMillis; }
}
