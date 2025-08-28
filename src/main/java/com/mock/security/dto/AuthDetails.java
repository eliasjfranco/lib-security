package com.mock.security.dto;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public record AuthDetails(Integer tenantId, WebAuthenticationDetails details) {
}
