package fr.cours.projkilmat.config;

import java.time.Duration;
import java.util.Objects;

/**
 * Représente la configuration immuable pour l'agent et sa connexion au modèle de langage.
 * L'utilisation d'un 'record' Java garantit que l'objet ne peut pas être modifié après sa création.
 * La validité de tous les paramètres est vérifiée au moment de la construction.
 */
// Un 'record' est une classe de données concise qui génère automatiquement le constructeur,
// les getters, ainsi que les méthodes equals(), hashCode() et toString().
public record AgentConfig(String baseUrl, String modelName, Duration timeout) {

    /**
     * Constructeur canonique compact du record.
     * Il est appelé automatiquement lors de la création d'une instance de AgentConfig.
     * Il contient la logique de validation pour s'assurer que la configuration est cohérente et valide.
     */
    public AgentConfig {
        // Vérifie que les paramètres ne sont pas nuls.
        Objects.requireNonNull(baseUrl, "L'URL de base (baseUrl) ne peut pas être nulle");
        Objects.requireNonNull(modelName, "Le nom du modèle (modelName) ne peut pas être nul");
        Objects.requireNonNull(timeout, "Le délai d'attente (timeout) ne peut pas être nul");

        // Vérifie que les chaînes de caractères obligatoires ne sont pas vides ou constituées uniquement d'espaces.
        if (baseUrl.isBlank() || modelName.isBlank()) {
            throw new IllegalArgumentException("L'URL de base et le nom du modèle ne doivent pas être vides");
        }
        // Vérifie que la durée du timeout est strictement positive.
        if (timeout.isNegative() || timeout.isZero()) {
            throw new IllegalArgumentException("Le délai d'attente (timeout) doit être positif");
        }
    }

    /**
     * Méthode de fabrique statique pour obtenir une nouvelle instance du Builder.
     * C'est le point d'entrée pour commencer à construire un objet AgentConfig.
     * @return Une nouvelle instance de Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Classe interne statique qui implémente le patron de conception (design pattern) "Builder" (ou "Monteur").
     * Ce patron facilite la création d'objets complexes en permettant de définir les attributs
     * de manière lisible et flexible, étape par étape.
     */
    public static class Builder {
        private String baseUrl;
        private String modelName;
        // Définit une valeur par défaut pour le timeout si l'utilisateur n'en spécifie pas.
        private Duration timeout = Duration.ofSeconds(60);

        /**
         * Définit l'URL de base de l'API du modèle de langage.
         * @param baseUrl L'URL (ex: "http://localhost:11434/").
         * @return L'instance actuelle du Builder pour permettre le chaînage des appels (fluent interface).
         */
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this; // Retourne 'this' pour chaîner les appels (ex: builder.baseUrl(...).modelName(...))
        }

        /**
         * Définit le nom du modèle à utiliser.
         * @param modelName Le nom du modèle (ex: "gemma3").
         * @return L'instance actuelle du Builder.
         */
        public Builder modelName(String modelName) {
            this.modelName = modelName;
            return this;
        }

        /**
         * Définit le délai d'attente maximum pour une réponse du modèle.
         * Si cette méthode n'est pas appelée, la valeur par défaut de 60 secondes sera utilisée.
         * @param timeout La durée.
         * @return L'instance actuelle du Builder.
         */
        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        /**
         * Construit l'objet AgentConfig final à partir des paramètres fournis au Builder.
         * C'est à ce moment que le constructeur du 'record' est appelé et que toutes les validations sont exécutées.
         * @return Une nouvelle instance immuable de AgentConfig.
         */
        public AgentConfig build() {
            return new AgentConfig(baseUrl, modelName, timeout);
        }
    }
}