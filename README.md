# Geekment Vôtre

**Le Jeu de Rôle s'invite chez vous.**

## Pourquoi utiliser Geekment Vôtre ?

- **Réservation Express** : Réservez un Maître du Jeu (MJ) professionnel pour vos soirées à domicile en quelques secondes.
- **Boutique Intégrée** : Achetez vos goodies préférés directement depuis l'application avec un panier fluide.
- **Sécurité & Confidentialité** : Vos données personnelles et vos adresses sont protégées par les meilleurs standards de sécurité et des politiques de confidentialité strictes.
- **Paiement Sécurisé** : Intégration Stripe pour des transactions sans friction sans stockage de coordonnées bancaires.
- **Soutenez l'Aventure** : Participez au financement de nouveaux univers et projets via notre section Crowdfunding intégrée.

---
## Spécifications techniques  

### Ce que permet l'application

L'application centralise toutes les interactions avec l'univers de **Geekement Vôtre** :

- **Shopping de Goodies** : Parcourir le catalogue, gérer un panier fluide et commander des articles exclusifs.
- **Tunnel d'Achat Sécurisé** : Validation des coordonnées en temps réel (nom, adresse, téléphone) et paiement sécurisé via Stripe.
- **Réservation de Sessions** : Planifier des parties de JDR à domicile en choisissant son créneau et le nombre de participants.
- **Crowdfunding** : Découvrir l'univers du JDR et participer à son financement directement dans l'app.
- **Gestion des Commandes** : Enregistrement automatique des clients et du détail des commandes (articles, quantités, statut de paiement).
- **Contact & Support** : Formulaire de contact intégré avec protection contre le langage offensant.
- **Confirmations Automatiques** : Réception d'e-mails de confirmation pour chaque achat ou réservation effectuée.

### Technologies utilisées

| Élément | Choix technique |
|--------|------------------|
| **Plateforme** | Android (Min SDK 26, Target SDK 34) |
| **Langage** | Kotlin 2.0 (Compose Compiler) |
| **UI Framework** | Jetpack Compose avec Material 3 |
| **Back-end** | Supabase (PostgreSQL, Postgrest, Edge Functions) |
| **Paiements** | Stripe Android SDK |
| **Emails** | Resend API (via Ktor) |
| **Networking** | Ktor Client & Kotlinx Serialization |
| **Sécurité** | Row Level Security (RLS) & Secrets Properties |

### Structure du dépôt

```
├── doc/                        # Documentation de conception
│   ├── bdd/                    # Modélisation (MCD, MLD, MPD)
│   ├── contraintes.md          # Spécifications techniques et graphiques
│   ├── regles-de-gestion.md    # Logique métier (Validations, etc.)
│   └── maquette/               # Designs de référence
├── src/                        # Code source Android
│   ├── app/src/main/java/com/geekementvotre/
│   │   ├── data/               # DTOs (Commande, Client, LigneCommande)
│   │   ├── ui/                 # Écrans (Shop, Checkout, Reservation)
│   │   └── viewmodels/         # Gestion d'état et validation
│   └── build.gradle.kts        # Dépendances
└── README.md
```

### Installation et Configuration

1. **Cloner le projet**
2. **Secrets** : Créer un fichier `src/secrets.properties` :
   ```properties
   supabase.url=https://votre-id.supabase.co
   supabase.anon_key=votre-cle-anonyme
   STRIPE_PUBLISHABLE_KEY=pk_test_...
   RESEND_API_KEY=re_...
   CONTACT_EMAIL_RECEIVER=admin@votremail.com
   ```
3. **Build** : Synchroniser avec Gradle et déployer.

## Documentation de référence

- **`doc/regles-de-gestion.md`** — Détails des 45 règles métier appliquées.
- **`doc/bdd/`** — Schéma complet de la base de données PostgreSQL.
