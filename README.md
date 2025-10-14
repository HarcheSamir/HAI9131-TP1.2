

# HAI9131 - Analyseur Statique de Code Java

Ce projet est un outil d'analyse statique pour le code source Java, développé pour le cours HAI9131. Il calcule diverses métriques de code et génère un graphe d'appel pour un projet Java cible.

Ce guide explique comment importer, configurer et exécuter le projet en utilisant l'IDE Eclipse.

## 1. Prérequis

Avant de commencer, assurez-vous d'avoir les logiciels suivants installés sur votre système :

1.  **Java Development Kit (JDK)** : Version 8 ou plus récente.
2.  **Eclipse IDE** : Le package "Eclipse IDE for Java Developers" est recommandé.
3.  **Graphviz** : **Ceci est obligatoire pour la fonctionnalité de génération de graphe d'appel (Question 14).**
    *   Téléchargez et installez-le depuis le site officiel : [https://graphviz.org/download/](https://graphviz.org/download/)
    *   **Étape Cruciale** : Après l'installation, vous devez ajouter le répertoire `bin` de Graphviz à la variable d'environnement `PATH` de votre système (par exemple, `C:\Program Files\Graphviz\bin`).

## 2. Instructions de Configuration dans Eclipse

Suivez ces étapes pour faire fonctionner le projet dans votre espace de travail Eclipse.

### Étape 1 : Importer le Projet dans Eclipse

1.  Lancez Eclipse et ouvrez votre espace de travail (workspace).
2.  Cliquez sur `File` -> `Import...`.
3.  Dans l'assistant d'importation, développez le dossier `Maven` et sélectionnez `Existing Maven Projects`. Cliquez sur `Next`.
4.  Cliquez sur le bouton `Browse...` et naviguez jusqu'au dossier racine du projet (`C:\HAI913I\HARCHE_Samir_TP1`).
5.  Eclipse devrait automatiquement détecter le fichier `pom.xml`. Assurez-vous que le projet est coché dans la liste et cliquez sur `Finish`.

### Étape 2 : Configurer le Chemin de Compilation (Build Path)

Le projet dépend des bibliothèques Eclipse JDT pour l'analyse du code, qui doivent être ajoutées manuellement au chemin de compilation du projet.

1.  Dans la vue `Package Explorer`, faites un clic droit sur le nom du projet (`hai913i`).
2.  Allez dans `Build Path` -> `Configure Build Path...`.
3.  Une nouvelle fenêtre s'ouvrira. Cliquez sur l'onglet `Libraries`.
4.  Sur le côté droit, cliquez sur le bouton `Add External JARs...`.
5.  Naviguez jusqu'au répertoire où vous avez stocké les bibliothèques nécessaires (par exemple, `C:\HAI913I\ast libs`).
6.  Sélectionnez tous les fichiers `.jar` dans ce répertoire et cliquez sur `Open`.
7.  Cliquez sur `Apply and Close`. Le projet devrait maintenant se compiler sans erreurs liées à des classes manquantes comme `ASTParser`.

### Étape 3 : TRÈS IMPORTANT - Mettre à jour le Chemin d'Accès Codé en Dur

Le projet est programmé pour rechercher le code à analyser dans un répertoire spécifique. Vous **devez** mettre à jour ce chemin pour qu'il corresponde à l'emplacement du projet sur votre ordinateur.

1.  **Ouvrez `Main.java`** :
    *   Naviguez vers `src/main/java` -> `tp1_HAI913I` -> `Main.java`.
    *   Trouvez la ligne suivante (vers la ligne 14) :
        ```java
        public static final String PROJECT_SOURCE_PATH = "C:\\HAI913I\\HARCHE_Samir_TP1\\src\\main\\java\\sample";
        ```
    *   **Modifiez ce chemin** pour qu'il corresponde au chemin absolu du dossier `sample` à l'intérieur du projet dans *votre* espace de travail.

2.  **Ouvrez `MainGUI.java`** :
    *   Naviguez vers `src/main/java` -> `tp1_HAI913I` -> `MainGUI.java`.
    *   Trouvez la ligne suivante (vers la ligne 18) :
        ```java
        public static final String PROJECT_SOURCE_PATH = "C:\\HAI913I\\HARCHE_Samir_TP1\\src\\main\\java\\sample";
        ```
    *   **Modifiez également ce chemin**, comme vous l'avez fait pour `Main.java`.

> **Astuce** : Dans le `Package Explorer` d'Eclipse, vous pouvez faire un clic droit sur le package `sample` -> `Properties`, et copier le chemin `Location` pour vous assurer qu'il est correct. N'oubliez pas d'utiliser des doubles antislashs (`\\`) dans la chaîne de caractères Java.

## 3. Lancer l'Application

Le projet a deux points d'entrée principaux : une version console et une version avec interface graphique.

### A. Lancer l'Application Console

1.  Dans le `Package Explorer`, naviguez vers `src/main/java` -> `tp1_HAI913I`.
2.  Faites un clic droit sur le fichier `Main.java`.
3.  Sélectionnez `Run As` -> `1 Java Application`.
4.  La vue `Console` d'Eclipse s'ouvrira, affichant un menu interactif où vous pouvez entrer un numéro de 0 à 14 pour exécuter une analyse.

### B. Lancer l'Application Graphique (GUI)

1.  Dans le `Package Explorer`, naviguez vers `src/main/java` -> `tp1_HAI913I`.
2.  Faites un clic droit sur le fichier `MainGUI.java`.
3.  Sélectionnez `Run As` -> `1 Java Application`.
4.  Une fenêtre intitulée "HAI913I - Interactive Java Analyzer" apparaîtra.
5.  Utilisez le menu déroulant pour sélectionner une analyse. Les résultats s'afficheront dans la zone de texte.

## 4. Dépannage

*   **Erreur : `java.lang.NoClassDefFoundError: org/eclipse/jdt/core/dom/ASTParser`**
    *   **Cause** : Les bibliothèques JDT n'ont pas été ajoutées correctement au chemin de compilation.
    *   **Solution** : Refaites l'**Étape 2 : Configurer le Chemin de Compilation**.

*   **Erreur : `FATAL ERROR: The hardcoded project path is invalid...` ou `Warning: No .java files were found.`**
    *   **Cause** : Le chemin d'accès dans `Main.java` et `MainGUI.java` est incorrect.
    *   **Solution** : Vérifiez et corrigez les chemins comme décrit à l'**Étape 3**.

*   **Erreur lors de la génération du graphe : `Cannot run program "dot"` ou `Graphviz 'dot' command failed...`**
    *   **Cause** : Graphviz n'est pas installé ou son répertoire `bin` n'est pas dans le `PATH` de votre système.
    *   **Solution** : Vérifiez votre installation de Graphviz et assurez-vous que la variable d'environnement `PATH` est correctement configurée. Vous devrez peut-être redémarrer Eclipse ou votre ordinateur après avoir modifié le `PATH`.
