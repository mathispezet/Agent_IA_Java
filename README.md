# Projet : Agent de Révision Java avec Ollama

Ce projet a été réalisé dans le cadre du cours de "Programmation Professionnelle". Il vise à concevoir et développer un agent intelligent capable d'assister un étudiant dans ses révisions de concepts de programmation Java, en s'appuyant sur un modèle de langage (LLM) exécuté localement via **Ollama**.

## 1. Objectif

L'objectif principal est de mettre en œuvre une **architecture logicielle claire, modulaire et robuste** en appliquant les bonnes pratiques de programmation vues en cours. L'application prend la forme d'un "Assistant de cours" qui interagit avec l'utilisateur en langage naturel via une simple console.

## 2. Fonctionnalités

L'agent est actuellement capable de réaliser deux tâches principales pour n'importe quel sujet Java fourni par l'utilisateur :

*   **Expliquer un concept** : L'agent fournit une explication simple, concise et pédagogique du sujet demandé.
*   **Créer un QCM** : L'agent génère une question à choix multiples (QCM) pertinente sur le sujet, avec 4 options et l'indication claire de la bonne réponse.

## 3. Architecture et Choix Techniques

Le projet est structuré en plusieurs modules distincts pour garantir une bonne séparation des responsabilités et une maintenabilité accrue.

### Librairies Principales
*   **Java 17+** : Langage principal du projet.
*   **LangChain4j** : Framework central qui simplifie l'interaction avec les modèles de langage. Il permet de définir les capacités de notre agent via une simple interface (`RevisionExpert`) et gère la communication avec le LLM de manière transparente.
*   **Ollama** : Backend d'inférence qui permet d'exécuter localement des modèles de langage open-source comme `gemma3`.

### Structure des Packages
Le code est organisé comme suit :
```
src/main/java/fr/cours/projkilmat/
├── agent/            # Logique métier de l'agent
│   ├── AgentFactory.java     # Crée l'instance de l'agent (Pattern Factory)
│   └── RevisionExpert.java   # Interface définissant les capacités de l'agent
├── config/           # Configuration de l'application
│   └── AgentConfig.java      # DTO immuable (record) pour les paramètres de l'agent (Pattern Builder)
├── ui/               # Interface utilisateur
│   └── ConsoleUI.java      # Gère les interactions en ligne de commande
├── shared/           # Classes utilitaires partagées
│   └── TechnicalException.java # Exception personnalisée pour la gestion d'erreurs
└── App.java          # Point d'entrée de l'application
```

### Patrons de Conception (Design Patterns)
*   **Factory Pattern** (`AgentFactory`) : Encapsule la logique de création de l'agent. Le reste de l'application n'a pas besoin de savoir comment l'agent est construit, ce qui le rend facile à modifier ou à remplacer.
*   **Builder Pattern** (`AgentConfig.Builder`) : Permet une création d'objet de configuration `AgentConfig` lisible, flexible et robuste, avec des valeurs par défaut et des validations intégrées.
*   **Injection de Dépendances** : L'instance de `RevisionExpert` est "injectée" dans `ConsoleUI` via son constructeur. L'interface utilisateur ne dépend que du contrat (l'interface) et non de l'implémentation, ce qui facilite les tests et l'évolution.
*   **DTO Immuable** (`AgentConfig` en tant que `record`) : Garantit que la configuration ne peut pas être modifiée après sa création, évitant ainsi les effets de bord.

## 4. Prérequis

Avant de lancer le projet, assurez-vous d'avoir installé les éléments suivants :

1.  **JDK 17** ou supérieur.
2.  **Maven** (pour la gestion des dépendances et la construction du projet).
3.  **Ollama** : Suivez les instructions sur [ollama.com](https://ollama.com/) pour l'installer sur votre système.

## 5. Installation et Lancement

1.  **Cloner le dépôt**
    ```sh
    git clone [URL_DE_VOTRE_DEPOT_GIT]
    cd [NOM_DU_REPERTOIRE]
    ```

2.  **Installer et lancer le modèle Ollama**
    Ouvrez un terminal et exécutez la commande suivante pour télécharger le modèle `gemma3` (utilisé dans ce projet) :
    ```sh
    ollama pull gemma3
    ```
    **Important** : Assurez-vous que l'application Ollama est en cours d'exécution avant de lancer le projet Java.

3.  **Compiler le projet**
    Utilisez Maven pour télécharger les dépendances et compiler le code source :
    ```sh
    mvn clean install
    ```

4.  **Exécuter l'application**
    Vous pouvez lancer l'application directement depuis votre IDE (en exécutant la méthode `main` de la classe `App.java`) ou via la ligne de commande :
    ```sh
    java -cp target/projkilmat-1.0-SNAPSHOT.jar fr.cours.projkilmat.App
    ```

## 6. Guide d'Utilisation

Une fois l'application lancée, une invitation apparaît dans la console :

```
=== Agent de Révision Java Professionnel ===

Entrez un sujet Java à réviser (ou 'quitter') :
```

*   Entrez un concept Java (par exemple, `Polymorphisme`, `Exceptions`, `Streams API`, etc.) et appuyez sur `Entrée`.
*   L'agent affichera d'abord une explication, puis générera un QCM sur ce sujet.
*   Pour arrêter le programme, tapez `quitter` et appuyez sur `Entrée`.

# TODO

Gerer l'import et l'analyse de fichier 
