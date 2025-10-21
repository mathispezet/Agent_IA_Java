package fr.cours.projkilmat;

import fr.cours.projkilmat.agent.AgentFactory;
import fr.cours.projkilmat.agent.RevisionExpert;
import fr.cours.projkilmat.config.AgentConfig;
import fr.cours.projkilmat.ui.ConsoleUI;

import java.time.Duration;

/**
 * Classe principale de l'application.
 */
public class App {
    /**
     * Méthode principale (point d'entrée) du programme.
     */
    public static void main(String[] args) {
        // 1. Créer la configuration pour l'application.
        // Cet objet contient les paramètres pour se connecter au modèle de langage (LLM).
        AgentConfig config = AgentConfig.builder()
                .baseUrl("http://localhost:11434/") // L'URL de base de l'API du modèle
                .modelName("gemma3:1b")                   // Le nom du modèle à utiliser
                .timeout(Duration.ofMinutes(2))    // Le temps d'attente maximum pour une réponse
                .build();

        // 2. Instancier la fabrique (Factory).
        // La fabrique est un patron de conception (design pattern) qui simplifie la création d'objets complexes.
        AgentFactory factory = new AgentFactory();

        // 3. Utiliser la fabrique pour créer l'agent conversationnel.
        // L'agent 'RevisionExpert' est créé en utilisant la configuration définie précédemment.
        RevisionExpert agent = factory.createAgent(config);

        // 4. Injecter l'agent dans l'interface utilisateur (UI) et démarrer l'application.
        // On passe l'agent au constructeur de l'UI (injection de dépendances) pour qu'elle puisse l'utiliser.
        ConsoleUI ui = new ConsoleUI(agent);

        // Lance la boucle principale de l'interface utilisateur en console.
        ui.start();
    }
}