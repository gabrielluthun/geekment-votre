# Étude d'Architecture Backend : Le choix pragmatique de Supabase

Ce document présente l'arbitrage technique concernant la couche Backend pour l'application **Geekement Vôtre**. L'objectif est de déployer une infrastructure sécurisée, capable de supporter un modèle de données relationnel (MERISE) restreint (moins de 10 tables), tout en optimisant le temps de développement.

---

## 1. Contexte et Philosophie d'Architecture

Le projet est géré par un développeur unique. La priorité architecturale est d'éviter la sur-ingénierie ("ne pas réinventer la roue") pour un périmètre de base de données réduit (Utilisateurs, Goodies, Commandes, Réservations).

Deux approches ont été confrontées :
1. **Développement sur mesure (Custom API Node.js) :** Création d'un serveur de zéro.
2. **Backend-as-a-Service (Supabase) :** Utilisation d'une infrastructure PostgreSQL infogérée avec génération automatique d'API.

---

## 2. Pourquoi écarter la "Custom API" ?

Bien qu'une API Node.js (Express/NestJS) offre un contrôle absolu, elle a été écartée pour les raisons suivantes :
* **Sur-ingénierie (Over-engineering) :** Pour une base de données de 4 à 5 tables, coder manuellement les routes CRUD, les contrôleurs, la gestion des tokens JWT et la configuration de l'ORM représente une perte de temps inutile.
* **Coût de maintenance :** Un serveur dédié nécessite de gérer le déploiement continu (CI/CD), la surveillance des failles de sécurité des dépendances (NPM) et la configuration du serveur (uptime).

---

## 3. L'adéquation parfaite de Supabase

Supabase s'impose comme le choix d'ingénierie optimal pour ce projet en combinant la puissance du SQL et la rapidité du Serverless.

### A. Respect strict du Modèle MERISE
Contrairement à Firebase (NoSQL), Supabase repose sur **PostgreSQL**. Le schéma conceptuel de données (MCD) modélisé en amont s'intègre nativement, garantissant l'intégrité relationnelle (clés étrangères entre Utilisateurs, Commandes et Produits) sans aucune dénormalisation.

### B. Gain de productivité (PostgREST)
Supabase génère instantanément une API REST sécurisée par-dessus la base de données. Le front-end (Ionic/Angular) peut requêter les tables directement via le SDK officiel, économisant ainsi des semaines de développement Backend. L'authentification (Email/Mot de passe) est également prête à l'emploi.

### C. Gestion des flux complexes (Edge Functions)
Pour les actions nécessitant une orchestration métier cachée du client (sécurité), Supabase propose des **Edge Functions** (basées sur Deno).
Ces fonctions serverless permettront de :
1. Réceptionner et valider les **Webhooks Stripe / PayPal** de manière sécurisée.
2. Exécuter la logique de synchronisation avec l'**API Google Calendar** (réservation de créneaux JDR).

---

## 4. Tableau de Synthèse

| Critère d'évaluation | Custom API (Node.js) | Supabase | Vainqueur |
| :--- | :--- | :--- | :--- |
| **Intégrité Relationnelle (SQL)** | Oui (via ORM) | Oui (Postgres Natif) | **Égalité** |
| **Vitesse de développement** | Faible (Tout coder) | Très Haute (API autogénérée) | **Supabase** |
| **Coût de maintenance** | Élevé | Faible (Infogéré) | **Supabase** |
| **Gestion des Webhooks Stripe** | Native (Serveur) | Native (Edge Functions) | **Égalité** |

---

## 5. Conclusion Technique

Le choix de la stack Backend se porte officiellement sur **Supabase**. Cette solution est parfaitement proportionnée à la taille de la base de données. Elle permet au développeur de se concentrer sur la valeur ajoutée (l'expérience utilisateur mobile et l'intégration métier) plutôt que sur la configuration d'une plomberie serveur standard, tout en garantissant une sécurité et une intégrité des données professionnelles.