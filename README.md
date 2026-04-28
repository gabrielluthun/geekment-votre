# Geekment Vôtre

Application mobile **Android** pour un prestataire de **jeux de rôle** (MJ à domicile) : vitrine, contact, réservation de parties, boutique de goodies et page crowdfunding.

## 🚀 État actuel du projet *(28/04/2026)*

Le projet est en cours de développement. Les éléments suivants sont déjà fonctionnels ou intégrés :
- **Architecture de base** : Navigation via `Scaffold` et `BottomBar` (Jetpack Compose).
- **Écran d'Accueil** : Vitrine présentant les prestations (MJ à domicile, Événementiel) et les univers de jeu.
- **Écran Boutique** : Système de chargement dynamique des produits via **Supabase Postgrest**.
- **Data Layer** : Intégration de `supabase-kt` avec gestion des états via `ViewModel` et `Flow`.

## 🛠 Périmètre technique

| Élément | Choix technique |
|--------|------------------|
| **Plateforme** | Android (Min SDK 25, Target SDK 36) |
| **Langage** | Kotlin 2.0 (Compose Compiler intégré) |
| **UI Framework** | Jetpack Compose avec Material 3 |
| **Back-end** | Supabase (PostgreSQL, Auth, Storage) |
| **Networking** | Ktor Client & Kotlinx Serialization |
| **Gestion d'état** | ViewModel & StateFlow |

## 📁 Structure du dépôt

```
├── doc/                        # Documentation de conception
│   ├── bdd/                    # Modélisation (MCD, MLD, MPD) et sources .loo
│   ├── contraintes.md          # Spécifications techniques et graphiques
│   ├── regles-de-gestion.md    # Logique métier (Boutique, JDR, Réservations)
│   ├── etude-stack/            # Comparatifs et justifications technologiques
│   └── maquette/               # Maquettes des écrans (Home, Boutique, etc.)
├── public/                     # Assets et ressources statiques
├── src/                        # Projet Android Studio
│   ├── app/                    # Module principal de l'application
│   │   └── src/main/java/com/geekementvotre/
│   │       ├── data/           # Modèles et repositories (Supabase)
│   │       ├── ui/             # Composants et écrans Compose
│   │       └── viewmodels/     # Logique de présentation
│   └── build.gradle.kts        # Configuration des dépendances
└── README.md
```

## ⚙️ Installation et Configuration

1. **Cloner le projet**
2. **Secrets** : Créer un fichier `src/secrets.properties` à la racine du projet Android avec vos identifiants Supabase :
   ```properties
   supabase.url=https://votre-id.supabase.co
   supabase.anon_key=votre-cle-anonyme
   ```
3. **Build** : Synchroniser le projet avec Gradle et lancer sur un émulateur ou appareil physique.

## 📖 Documentation utile

- **`doc/bdd/`** — Modélisation de la base de données.
- **`doc/etude-stack/`** — Justification du choix **Kotlin Natif** + **Supabase**.
- **`doc/regles-de-gestion.md`** — Détail des fonctionnalités métier attendues.
