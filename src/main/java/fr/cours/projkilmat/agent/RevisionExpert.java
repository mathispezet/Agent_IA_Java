package fr.cours.projkilmat.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * Defines the contract for our Revision Agent using LangChain4j's AI Service.
 * Each method represents a distinct capability (a "strategy").
 */
public interface RevisionExpert {

    @SystemMessage(
            "Tu es un expert en programmation, pédagogue et précis. " +
                    "Ton objectif est d'expliquer des concepts techniques de manière simple et concise à un étudiant."
    )
    @UserMessage("Explique le concept suivant : {{topic}}")
    String explain(@V("topic") String topic);

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
}