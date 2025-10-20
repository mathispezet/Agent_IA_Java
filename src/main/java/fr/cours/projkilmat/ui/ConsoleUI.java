package fr.cours.projkilmat.ui;

import fr.cours.projkilmat.agent.RevisionExpert;
import fr.cours.projkilmat.shared.TechnicalException;

import java.util.Objects;
import java.util.Scanner;

/**
 * Gère l'interface utilisateur en mode console (CLI - Command Line Interface).
 * Cette classe est responsable de l'affichage des menus, de la lecture des entrées utilisateur
 * et de l'orchestration des appels à l'agent conversationnel.
 */
public class ConsoleUI {

    // L'agent (le "cerveau") qui sera utilisé par l'interface utilisateur.
    // Le mot-clé 'final' indique qu'il ne peut être réassigné après sa construction.
    private final RevisionExpert agent;

    /**
     * Constructeur de l'interface utilisateur.
     * Il reçoit l'agent en tant que dépendance (c'est le principe de l'injection de dépendances).
     * @param agent L'instance de RevisionExpert à utiliser. Ne peut pas être nul.
     */
    public ConsoleUI(RevisionExpert agent) {
        // Objects.requireNonNull est une bonne pratique pour s'assurer que les dépendances essentielles
        // sont bien fournies à la création de l'objet (principe du "fail-fast" ou échec rapide).
        this.agent = Objects.requireNonNull(agent, "L'agent RevisionExpert ne peut pas être nul");
    }

    /**
     * Lance la boucle principale de l'application en console.
     * Attend les entrées de l'utilisateur et interagit avec l'agent jusqu'à ce que l'utilisateur tape 'quitter'.
     */
    public void start() {
        // Le "try-with-resources" garantit que le Scanner sera automatiquement fermé à la fin du bloc,
        // même en cas d'erreur, évitant ainsi les fuites de ressources.
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Agent de Révision Java Professionnel ===");
            // Boucle infinie pour maintenir le programme actif et attendre les commandes de l'utilisateur.
            while (true) {
                System.out.print("\nEntrez un sujet Java à réviser (ou 'quitter' pour arrêter) : ");
                String topic = scanner.nextLine();

                // Condition de sortie de la boucle. 'equalsIgnoreCase' ignore la casse (majuscules/minuscules).
                if ("quitter".equalsIgnoreCase(topic)) {
                    break; // Sort de la boucle while.
                }

                // Si l'utilisateur appuie sur Entrée sans rien taper, on redemande un sujet.
                if (topic.isBlank()) {
                    continue; // Passe à l'itération suivante de la boucle.
                }

                // Délègue le traitement du sujet à une méthode dédiée pour une meilleure organisation du code.
                processTopic(topic);
            }
        }
        System.out.println("Au revoir !");
    }

    /**
     * Orchestre les appels à l'agent pour un sujet donné.
     * Appelle successivement les différentes fonctionnalités de l'agent (explication, QCM).
     * @param topic Le sujet fourni par l'utilisateur.
     */
    private void processTopic(String topic) {
        try {
            // Première interaction : demander une explication du concept.
            System.out.println("\n--- Explication du concept ---");
            System.out.println("Génération en cours, veuillez patienter...");
            String explanation = agent.explain(topic); // Appel à la méthode 'explain' de l'agent
            System.out.println(explanation);

            // Deuxième interaction : demander la création d'un QCM.
            System.out.println("\n--- Génération d'un QCM ---");
            System.out.println("Génération en cours, veuillez patienter...");
            String qcm = agent.createQcm(topic); // Appel à la méthode 'createQcm' de l'agent
            System.out.println(qcm);

        } catch (Exception e) {
            // Intercepte les exceptions qui pourraient survenir lors de la communication avec l'API du LLM (ex: service indisponible).
            // Les exceptions de LangChain4j sont des RuntimeException, d'où le 'catch (Exception e)'.
            // On encapsule l'exception technique dans une exception propre à notre application pour une meilleure gestion des erreurs.
            throw new TechnicalException("Une erreur est survenue lors de l'interaction avec l'agent.", e);
        }
    }
}