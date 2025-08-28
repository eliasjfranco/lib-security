package com.mock.security.config;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

final class PublicEndpointsCustomizer {

    private PublicEndpointsCustomizer() { }

    static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>
    permitAll(String... paths) {
        return auth -> {
            if (paths != null && paths.length > 0) {
                auth.requestMatchers(paths).permitAll();
            }
            auth.anyRequest().authenticated();
        };
    }
}
