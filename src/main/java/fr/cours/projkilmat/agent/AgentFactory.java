package fr.cours.projkilmat.agent;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import fr.cours.projkilmat.config.AgentConfig;

import java.util.Objects;

/**
 * Classe de type Fabrique (Factory) dont le rôle est de créer une instance de l'agent {@link RevisionExpert}.
 * Elle abstrait et encapsule la logique de configuration et d'initialisation
 * de l'agent avec le framework LangChain4j.
 */
public class AgentFactory {

    /**
     * Crée et configure une instance de l'agent {@link RevisionExpert}.
     *
     * @param config L'objet de configuration contenant les paramètres nécessaires (URL, nom du modèle, etc.).
     * @return Une instance de {@link RevisionExpert} prête à être utilisée.
     */
    public RevisionExpert createAgent(AgentConfig config) {
        // S'assure que la configuration fournie n'est pas nulle pour éviter les erreurs.
        Objects.requireNonNull(config, "L'objet AgentConfig ne peut pas être nul");

        // --- Étape 1 : Construire le modèle de conversation sous-jacent ---
        // Cette partie crée l'objet technique qui communique avec l'API du grand modèle de langage (LLM).
        // Ici, nous utilisons l'implémentation pour Ollama.
        ChatModel model = OllamaChatModel.builder()
                .baseUrl(config.baseUrl())       // Définit l'adresse du serveur Ollama.
                .modelName(config.modelName())   // Spécifie quel modèle utiliser (ex: "gemma3").
                .timeout(config.timeout())       // Configure le temps d'attente maximum pour une réponse.
                .build();

        // --- Étape 2 : Créer et retourner le service d'IA (l'agent) ---
        // AiServices prend notre interface Java (RevisionExpert.class) et le modèle de communication (model),
        // puis il génère dynamiquement une classe qui implémente cette interface.
        // Chaque appel à une méthode de l'interface (ex: agent.explain("sujet")) sera automatiquement
        // transformé en une requête au LLM, en se basant sur les annotations (@SystemMessage, @UserMessage).
        return AiServices.create(RevisionExpert.class, model);
    }
}