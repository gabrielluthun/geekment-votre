# Geekment Vôtre

Application mobile **Android** pour un prestataire de **jeux de rôle** (MJ à domicile) : vitrine, contact, réservation de parties, boutique de goodies et page crowdfunding.

## Objectif produit

- Mettre en avant l’activité et l’expérience du client, avec le logo de la marque.
- Convertir : contact, réservation de créneaux (lien avec **Google Agenda**), achat de produits (**PayPal** en priorité, **Stripe** en secours), participation au financement via **redirection web**.
- Interface **mobile-first**, alignée sur la charte du site [geekementvotre.fr](https://www.geekementvotre.fr).

## Périmètre technique

| Élément | Choix documenté |
|--------|------------------|
| Plateforme | **Android** |
| Front mobile | **Kotlin natif** (Jetpack Compose) |
| Back-end | **Supabase** (PostgreSQL, Auth, Edge Functions) |
| Déploiement | SQL script déployé sur Supabase |

## Structure du dépôt

```
├── doc/
│   ├── bdd/                    # Modélisation (MCD, MLD, MPD) et fichiers source
│   ├── contraintes.md          # Contraintes techniques, financières, design, fonctionnelles
│   ├── regles-de-gestion.md    # Règles métier (boutique, JDR, réservation, crowdfunding)
│   ├── etude-stack/
│   │   ├── front.md            # Choix de Kotlin
│   │   └── back.md             # Choix de Supabase
│   └── maquette/               # Maquettes écran (PNG)
├── public/                     # Assets statiques
├── src/                        # Code source (Android Studio Project)
└── README.md
```

## Documentation utile

- **`doc/bdd/`** — Modélisation de la base de données (MCD v1.0.0, MPD).
- **`doc/etude-stack/back.md`** — Justification du choix **Supabase** et architecture backend.
- **`doc/contraintes.md`** — Intégrations (Agenda, PayPal, Stripe), charte graphique.
- **`doc/regles-de-gestion.md`** — Détail des fonctionnalités métier.
