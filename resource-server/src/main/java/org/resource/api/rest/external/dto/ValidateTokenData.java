package org.resource.api.rest.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ValidateTokenData {
    @JsonProperty("validateToken")
    private ValidateTokenResult validateToken;

    @Data
    public static class ValidateTokenResult {
        private boolean valid;
        private String username;
    }
}
