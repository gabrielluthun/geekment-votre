# Geekment Vôtre

**Le Jeu de Rôle s'invite chez vous.**

## Pourquoi utiliser Geekment Vôtre ?

- **Réservation Express** : Réservez un Maître du Jeu (MJ) professionnel pour vos soirées à domicile en quelques secondes.
- **Sécurité & Confidentialité** : Vos données personnelles et vos adresses sont protégées par les meilleurs standards de sécurité et des politiques de confidentialité strictes.
- **Soutenez l'Aventure** : Participez au financement de nouveaux univers et projets via notre section Crowdfunding intégrée.
- **Contact Direct** : Une question ? Un besoin spécifique ? Envoyez un message directement via l'application.

---
## Spécifications techniques  

### État actuel du projet *(Dernière mise à jour : 02/06/2026)*

Le projet est dans une phase avancée de développement. Les éléments suivants sont fonctionnels :
- **Architecture & Navigation** : Navigation moderne via `Scaffold` et `BottomBar` (Jetpack Compose).
- **Écran d'Accueil** : Vitrine présentant les prestations et les univers de jeu.
- **Boutique & Goodies** : Chargement dynamique des produits depuis **Supabase Postgrest**.
- **Système de Réservation** : Formulaire complet avec validations strictes.
- **Système de Contact** : Formulaire sécurisé avec filtre de langage offensant.
- **Notifications E-mail** : Intégration de **Resend** pour l'envoi de confirmations automatiques (Admin/Client).
- **Sécurité & Optimisation** : Protection Row Level Security (RLS) active, obfuscation **R8/ProGuard** activée, et optimisation des requêtes `upsert().select()`.

### Technologies utilisées

| Élément | Choix technique |
|--------|------------------|
| **Plateforme** | Android (Min SDK 26, Target SDK 36) |
| **Langage** | Kotlin 2.0 (Compose Compiler) |
| **UI Framework** | Jetpack Compose avec Material 3 |
| **Back-end** | Supabase (PostgreSQL, Auth, Storage) |
| **Emails** | Resend API (via Ktor) |
| **Networking** | Ktor Client & Kotlinx Serialization |
| **Sécurité** | R8/ProGuard & Supabase RLS |

### Structure du dépôt

```
├── doc/                        # Documentation de conception
│   ├── bdd/                    # Modélisation (MCD, MLD, MPD) et sources .loo
│   ├── contraintes.md          # Spécifications techniques et graphiques
│   ├── regles-de-gestion.md    # Écrit de la logique métier
│   ├── etude-stack/            # Comparatifs et justifications technologiques
│   └── maquette/               # Maquettes des écrans (Home, Boutique, etc.)
├── public/                     # Assets et ressources statiques
├── src/                        # Projet Android Studio
│   ├── app/                    # Module principal
│   │   └── src/main/java/com/geekementvotre/
│   │       ├── data/           # Modèles et repositories
│   │       ├── ui/             # Écrans et composants Compose
│   │       └── viewmodels/     # Logique de présentation et validations
│   └── build.gradle.kts        # Configuration des dépendances
└── README.md
```

### Installation et Configuration

1. **Cloner le projet**
2. **Secrets** : Créer un fichier `src/secrets.properties` à la racine du projet Android :
   ```properties
   supabase.url=https://votre-id.supabase.co
   supabase.anon_key=votre-cle-anonyme
   RESEND_API_KEY=votre_cle_api_resend
   CONTACT_EMAIL_RECEIVER=votre@email.com
   ```
3. **Build** : Synchroniser Gradle et lancer sur un appareil compatible (physique ou virtuel).

## Documentation de référence

- **`doc/regles-de-gestion.md`** — Crucial pour comprendre les validations des formulaires.
- **`doc/bdd/`** — Structure des tables PostgreSQL.
