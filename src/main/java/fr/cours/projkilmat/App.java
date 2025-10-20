package fr.cours.projkilmat;

import fr.cours.projkilmat.agent.AgentFactory;
import fr.cours.projkilmat.agent.RevisionExpert;
import fr.cours.projkilmat.config.AgentConfig;
import fr.cours.projkilmat.ui.ConsoleUI;

import java.time.Duration;

public class App {
    public static void main(String[] args) {
        // 1. Create the configuration for the application
        AgentConfig config = AgentConfig.builder()
                .baseUrl("http://localhost:11434/")
                .modelName("llama3")
                .timeout(Duration.ofMinutes(2))
                .build();

        // 2. Instantiate the factory
        AgentFactory factory = new AgentFactory();

        // 3. Use the factory to create the agent service
        RevisionExpert agent = factory.createAgent(config);

        // 4. Inject the agent into the UI and start the application
        ConsoleUI ui = new ConsoleUI(agent);
        ui.start();
    }
}