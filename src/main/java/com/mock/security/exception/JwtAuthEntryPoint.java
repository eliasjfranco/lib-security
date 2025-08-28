package com.mock.security.exception;

import com.mock.security.utils.ObjectMapperUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(ObjectMapperUtils.mapToString(getBody(request)));
    }

    private Map<String, Object> getBody(HttpServletRequest request) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        data.put("message", "You must authenticate to access this resource");
        data.put("body", "Unauthorized");
        data.put("path", request.getRequestURI());
        return data;
    }
}
