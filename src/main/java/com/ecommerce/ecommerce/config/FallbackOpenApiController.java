/*
package com.ecommerce.ecommerce.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

*/
/**
 * Optional fallback OpenAPI endpoint to serve minimal API docs if springdoc fails.
 * Enable by setting application property `app.swagger.fallback=true`.
 * This endpoint is intentionally opt-in to avoid conflicting with springdoc when present.
 *//*

@RestController
@ConditionalOnProperty(prefix = "app.swagger", name = "fallback", havingValue = "true", matchIfMissing = false)
public class FallbackOpenApiController {

    @GetMapping(value = "/v3/api-docs", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> apiDocs() {
        Map<String, Object> root = new HashMap<>();
        root.put("openapi", "3.0.1");
        Map<String, Object> info = new HashMap<>();
        info.put("title", "Ecommerce API - Fallback");
        info.put("version", "0.0.1");
        info.put("description", "Fallback OpenAPI JSON because automatic generation is unavailable");
        root.put("info", info);
        root.put("paths", new HashMap<>());
        return root;
    }
}
*/
