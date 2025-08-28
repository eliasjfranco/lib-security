package com.mock.security.filter;

import com.mock.security.dto.AuthDetails;
import com.mock.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HEADER_AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            final String username = jwtService.extractUsername(token);
            final Integer tenantId = jwtService.extractTenantId(token);
            final String role = jwtService.extractRole(token);

            Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            authentication.setDetails(new AuthDetails(tenantId, new WebAuthenticationDetails(request)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ignored) {}

        filterChain.doFilter(request, response);
    }
}
