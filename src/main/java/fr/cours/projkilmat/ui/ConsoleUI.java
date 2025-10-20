package fr.cours.projkilmat.ui;

import fr.cours.projkilmat.agent.RevisionExpert;
import fr.cours.projkilmat.shared.TechnicalException;

import java.util.Objects;
import java.util.Scanner;

public class ConsoleUI {
    private final RevisionExpert agent;

    // Dependency is injected via the constructor
    public ConsoleUI(RevisionExpert agent) {
        this.agent = Objects.requireNonNull(agent, "RevisionExpert agent cannot be null");
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Agent de Révision Java Professionnel ===");
            while (true) {
                System.out.print("\nEntrez un sujet Java à réviser (ou 'quitter') : ");
                String topic = scanner.nextLine();
                if ("quitter".equalsIgnoreCase(topic)) {
                    break;
                }

                if (topic.isBlank()) continue;

                processTopic(topic);
            }
        }
        System.out.println("Au revoir !");
    }

    private void processTopic(String topic) {
        try {
            System.out.println("\n--- Explication du concept ---");
            System.out.println("Génération en cours...");
            String explanation = agent.explain(topic);
            System.out.println(explanation);

            System.out.println("\n--- Génération d'un QCM ---");
            System.out.println("Génération en cours...");
            String qcm = agent.createQcm(topic);
            System.out.println(qcm);

        } catch (Exception e) {
            // Catching LangChain4j runtime exceptions
            throw new TechnicalException("Une erreur est survenue lors de l'interaction avec l'agent.", e);
        }
    }
}