package com.example.recipebackend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Basic root endpoints and docs redirect.
 */
@RestController
@Tag(name = "Hello Controller", description = "Basic endpoints for recipebackend")
public class HelloController {

    // PUBLIC_INTERFACE
    @GetMapping("/")
    @Operation(summary = "Welcome endpoint", description = "Returns a welcome message")
    public String hello() {
        return "Hello, Spring Boot! Welcome to recipebackend";
    }

    // PUBLIC_INTERFACE
    @GetMapping("/docs")
    @Operation(summary = "API Documentation", description = "Redirects to Swagger UI preserving original scheme/host/port")
    public RedirectView docs(HttpServletRequest request) {
        // Build an absolute URL based on the incoming request, honoring X-Forwarded-* headers
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null || scheme.isBlank()) scheme = request.getScheme();

        String host = request.getHeader("X-Forwarded-Host");
        String serverName;
        int serverPort;
        if (host != null && !host.isBlank()) {
            // If host includes port, keep it
            serverName = host;
            serverPort = -1; // already included in host if applicable
        } else {
            serverName = request.getServerName();
            serverPort = request.getServerPort();
        }

        UriComponentsBuilder b = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(serverName);
        if (serverPort > 0) {
            b.port(serverPort);
        }
        String target = b.replacePath("/swagger-ui.html")
                .replaceQuery(null)
                .build()
                .toUriString();

        RedirectView rv = new RedirectView(target);
        // Use HTTP 1.1 compatible redirects when necessary (preserves 303/307 semantics if used)
        rv.setHttp10Compatible(false);
        return rv;
    }

    // PUBLIC_INTERFACE
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Returns application health status")
    public String health() {
        return "OK";
    }

    // PUBLIC_INTERFACE
    @GetMapping("/api/info")
    @Operation(summary = "Application info", description = "Returns application information")
    public String info() {
        return "Spring Boot Application: recipebackend";
    }
}