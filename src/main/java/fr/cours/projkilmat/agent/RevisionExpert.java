package fr.cours.projkilmat.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;


/**
 * Définit le contrat de service pour notre agent "RevisionExpert" en utilisant LangChain4j.
 * LangChain4j va automatiquement créer une implémentation de cette interface.
 * Chaque méthode déclarée ici correspond à une capacité / fonctionnalité de l'agent.
 */
public interface RevisionExpert {

    /**
     * Demande à l'agent d'expliquer un concept technique spécifique.
     * @param topic Le sujet ou le concept à expliquer.
     * @return Une chaîne de caractères contenant l'explication fournie par l'agent.
     */
    // @SystemMessage définit le rôle et la personnalité de l'agent pour cette interaction.
    // C'est une instruction qui n'apparaît pas dans la conversation, mais qui guide le modèle.
    @SystemMessage(
            "Tu es un expert en programmation, pédagogue et précis. " +
                    "Ton objectif est d'expliquer des concepts techniques de manière simple et concise à un étudiant."
    )
    // @UserMessage est le modèle du message qui sera envoyé par l'utilisateur.
    @UserMessage("Explique le concept suivant : {{topic}}")
    // La méthode qui sera appelée dans notre code Java.
    // @V("topic") lie le paramètre 'topic' de la méthode à la variable {{topic}} dans le @UserMessage.
    String explain(@V("topic") String topic);

    /**
     * Demande à l'agent de créer une question à choix multiples (QCM) sur un sujet donné.
     * @param topic Le sujet sur lequel le QCM doit porter.
     * @return Une chaîne de caractères formatée contenant la question, les 4 options et la réponse.
     */
    @SystemMessage(
            "Tu es un créateur de quiz expérimenté. " +
                    "Ton objectif est de créer une question à choix multiples (QCM) pertinente " +
                    "avec 4 options (A, B, C, D) et une seule bonne réponse."
    )
    @UserMessage(
            "Génère un QCM sur le sujet suivant : {{topic}}. " +
                    "Indique clairement la bonne réponse à la fin, sur une ligne séparée, après la mention 'Réponse : '."
    )
    String createQcm(@V("topic") String topic);

    /*
     * Pistes d'améliorations futures pour cet agent.
     * TODO: Rajouter la gestion de la difficulté pour les QCM (par exemple, en ajoutant un paramètre 'difficulty').
     * TODO: Permettre à l'utilisateur de choisir le nombre de questions pour le QCM.
     * TODO: Rajouter une nouvelle méthode pour la création de fiches de révision structurées.
     */
}