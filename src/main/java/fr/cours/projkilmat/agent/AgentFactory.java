package fr.cours.projkilmat.agent;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import fr.cours.projkilmat.config.AgentConfig;

import java.util.Objects;

/**
 * A factory responsible for creating instances of the RevisionExpert agent.
 * It encapsulates the LangChain4j framework's setup and wiring logic.
 */
public class AgentFactory {

    public RevisionExpert createAgent(AgentConfig config) {
        Objects.requireNonNull(config, "AgentConfig cannot be null");

        // 1. Build the underlying language model infrastructure
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl(config.baseUrl())
                .modelName(config.modelName())
                .timeout(config.timeout())
                .build();

        // 2. Create and return the AI Service proxy instance
        return AiServices.create(RevisionExpert.class, model);
    }
}