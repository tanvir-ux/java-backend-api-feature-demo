package com.tanvir.featuredemo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Stub JWT filter — accepts {@code Authorization: Bearer demo-token} when JWT mode is on.
 * Replace with a real JJWT/Nimbus verifier before production use.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    public static final String DEMO_TOKEN = "demo-token";

    private final JwtProperties jwtProperties;

    public JwtAuthFilter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (!jwtProperties.isEnabled()) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7).trim();
            if (DEMO_TOKEN.equals(token) || token.equals(jwtProperties.getSecret())) {
                var auth = new UsernamePasswordAuthenticationToken(
                        "jwt-user",
                        null,
                        AuthorityUtils.createAuthorityList("ROLE_USER")
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
