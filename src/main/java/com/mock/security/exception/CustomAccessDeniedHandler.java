package com.mock.security.exception;

import com.mock.security.utils.ObjectMapperUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(ObjectMapperUtils.mapToString(getBody(request)));
    }

    private Map<String, Object> getBody(HttpServletRequest request) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("status", HttpServletResponse.SC_FORBIDDEN);
        data.put("body", "Unauthorized");
        data.put("message", "You are not allowed to access this resource");
        data.put("path", request.getRequestURI());
        return data;
    }
}
