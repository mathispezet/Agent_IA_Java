package fr.cours.projkilmat.config;

import java.time.Duration;
import java.util.Objects;

/**
 * Immutable configuration for the agent and its underlying infrastructure.
 * Ensures that all settings are valid upon construction.
 */
public record AgentConfig(String baseUrl, String modelName, Duration timeout) {

    public AgentConfig {
        Objects.requireNonNull(baseUrl, "Base URL cannot be null");
        Objects.requireNonNull(modelName, "Model name cannot be null");
        Objects.requireNonNull(timeout, "Timeout cannot be null");
        if (baseUrl.isBlank() || modelName.isBlank()) {
            throw new IllegalArgumentException("Base URL and model name must not be blank");
        }
        if (timeout.isNegative() || timeout.isZero()) {
            throw new IllegalArgumentException("Timeout must be positive");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String baseUrl;
        private String modelName;
        private Duration timeout = Duration.ofSeconds(60); // Default value

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder modelName(String modelName) {
            this.modelName = modelName;
            return this;
        }

        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        public AgentConfig build() {
            return new AgentConfig(baseUrl, modelName, timeout);
        }
    }
}