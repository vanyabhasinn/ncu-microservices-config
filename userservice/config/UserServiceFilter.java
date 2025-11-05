package com.ncu.library.userservice.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ncu.library.userservice.dto.AuthDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserServiceFilter extends OncePerRequestFilter {

    @Value("${apigateway.shared.secret}")
    private String _SharedSecret;

    @Value("${authservice.url}")
    private String authServiceUrl; // e.g., http://authservice/auth/authenticate

    private final RestClient.Builder restClientBuilder;

    public UserServiceFilter(RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // ✅ 1. Allow actuator and health endpoints without auth
        if (path.startsWith("/actuator")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ✅ 2. Allow gateway requests verified by shared secret
        final String secret = request.getHeader("X-API-GATEWAY-SECRET");
        if (secret != null && _SharedSecret.equals(secret)) {
            filterChain.doFilter(request, response);
            return;
        }

        // ✅ 3. Check Authorization header for direct access
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // ✅ 4. Decode credentials
        String base64Credentials = authHeader.substring("Basic ".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

        String[] parts = decodedString.split(":", 2);
        String username = parts[0];
        String password = parts.length > 1 ? parts[1] : "";

        try {
            // ✅ 5. Validate credentials with AuthService
            AuthDto authDto = new AuthDto(username, password);

            ResponseEntity<Void> responseEntity = restClientBuilder.build()
                    .post()
                    .uri(authServiceUrl)
                    .body(authDto)
                    .retrieve()
                    .toBodilessEntity();

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
