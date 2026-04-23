# Étude d'Architecture : App Native Kotlin & Backend Supabase

Ce document présente l'arbitrage technique concernant la stack applicative pour **Geekement Vôtre**. L'objectif est de déployer une infrastructure robuste, capable de supporter un modèle de données relationnel (MERISE) restreint (moins de 10 tables), tout en s'intégrant parfaitement avec un front-end mobile natif.

---

## 1. Contexte et Philosophie d'Architecture

Le projet est développé nativement pour Android via **Kotlin**. La priorité architecturale côté serveur est d'éviter la sur-ingénierie ("ne pas réinventer la roue") pour un périmètre de base de données réduit (Utilisateurs, Goodies, Commandes, Réservations).

Deux approches Backend ont été confrontées :
1. **Développement sur mesure (Custom API Node.js/Java) :** Création d'un serveur de zéro.
2. **Backend-as-a-Service (Supabase) :** Utilisation d'une infrastructure PostgreSQL infogérée avec génération automatique d'API.

---

## 2. L'intégration optimale avec Kotlin

Le choix de **Supabase** s'impose non seulement pour la gestion de la base de données, mais particulièrement pour son excellente synergie avec le développement Android moderne.

* **SDK Kotlin Natif (`supabase-kt`) :** Supabase propose un client Kotlin robuste, pensé pour l'écosystème Android actuel.
* **Coroutines & Ktor :** Les appels réseau vers l'API générée par Supabase sont gérés nativement via les Coroutines Kotlin, évitant de bloquer le thread principal (UI) lors des requêtes HTTP sans nécessiter la configuration manuelle de bibliothèques comme Retrofit.
* **Temps Réel avec Kotlin Flows :** La synchronisation des données (ex: statut d'une commande ou pourcentage du crowdfunding) s'effectue via les Flows, permettant de mettre à jour l'interface Jetpack Compose ou XML de manière réactive.

---

## 3. Le respect du Modèle MERISE et la Productivité

Contrairement aux solutions NoSQL, Supabase repose sur **PostgreSQL**. Le schéma conceptuel de données (MCD) modélisé en amont s'intègre nativement, garantissant l'intégrité relationnelle (clés étrangères entre Utilisateurs, Commandes et Produits) sans aucune dénormalisation.

L'outil génère instantanément une API REST sécurisée par-dessus la base de données. L'application Kotlin peut requêter les tables directement, économisant le développement complet d'une couche contrôleur/ORM côté serveur. L'authentification est également prête à l'emploi.

---

## 4. Gestion des flux complexes (Edge Functions)

Pour les actions nécessitant une orchestration métier cachée du client mobile (pour des raisons de sécurité), Supabase propose des **Edge Functions**.
Ces micro-services permettront de :
1. Réceptionner et valider les **Webhooks Stripe / PayPal** de manière sécurisée sans exposer les clés secrètes dans le code Kotlin.
2. Exécuter la logique de synchronisation avec l'**API Google Calendar** (réservation de créneaux JDR).

---

## 5. Conclusion Technique

Le choix de la stack se porte officiellement sur **Kotlin (Front-end Android) couplé à Supabase (Back-end PostgreSQL)**. Cette architecture est parfaitement proportionnée au périmètre du projet. Elle permet de conserver des performances mobiles natives de haut niveau tout en déléguant la gestion d'infrastructure serveur. Le développeur peut ainsi se concentrer sur l'intégration métier et l'expérience utilisateur, tout en garantissant une sécurité et une intégrité des données professionnelles.