# Geekment Vôtre

Application mobile **Android** pour un prestataire de **jeux de rôle** (MJ à domicile) : vitrine, contact, réservation de parties, boutique de goodies et page crowdfunding.

## Objectif produit

- Mettre en avant l’activité et l’expérience du client, avec le logo de la marque.
- Convertir : contact, réservation de créneaux (lien avec **Google Agenda**), achat de produits (**PayPal** en priorité, **Stripe** en secours), participation au financement via **redirection web** (pas de paiement crowdfunding in-app).
- Interface **mobile-first**, alignée sur la charte du site [geekementvotre.fr](https://www.geekementvotre.fr), mais peut être améliorée (objectif : rendu plus professionnel que le site actuel).

## Périmètre technique

| Élément | Choix documenté |
|--------|------------------|
| Plateforme | **Android uniquement** (pas d’iOS dans le périmètre initial) |
| Front mobile | **Kotlin natif** (recommandation principale dans `doc/etude-stack/front.md`) |
| Publication | Cible **Google Play** (pas d’App Store prévu dans les contraintes budgétaires) |

Le code applicatif n’est pas encore initialisé dans ce dépôt : les dossiers `src/` et `public/` sont des emplacements réservés.

## Structure du dépôt

```
├── doc/
│   ├── contraintes.md          # contraintes techniques, financières, design, fonctionnelles
│   ├── regles-de-gestion.md    # règles métier (boutique, JDR, réservation, crowdfunding)
│   ├── etude-stack/
│   │   └── front.md            # comparatif Flutter / Kotlin / React Native et choix Kotlin
│   └── maquette/               # maquettes écran (PNG)
├── public/                     # assets statiques (à définir)
├── src/                        # code source (à initialiser)
└── README.md
```

## Documentation utile

- **`doc/contraintes.md`** — intégrations (Agenda, PayPal, Stripe), crowdfunding par lien, charte graphique, etc.
- **`doc/regles-de-gestion.md`** — détail des fonctionnalités (onglets footer, boutique sans compte obligatoire avec email / facturation, formulaire de réservation JDR, etc.).
- **`doc/etude-stack/front.md`** — justification du choix **Kotlin** pour le front Android.

