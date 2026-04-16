# Comparatif Flutter vs Kotlin vs React Native

## Hypothèses de comparaison

- Cible : **Android uniquement** (pas iOS à court terme)
- Objectif : app vitrine + conversion (contact, réservation, goodies, crowdfunding)
- Niveau attendu : produit professionnel, maintenable sur la durée

## Barème

- `★★★★★` = excellent
- `★★★★☆` = très bon
- `★★★☆☆` = bon / correct
- `★★☆☆☆` = limité
- `★☆☆☆☆` = faible

## Tableau multi-critères

| Critère | Flutter | Kotlin (Android natif) | React Native |
|---|---|---|---|
| Performance UI (fluidité, animations) | `★★★★☆` | `★★★★★` | `★★★☆☆` |
| Performance runtime (CPU/mémoire) | `★★★★☆` | `★★★★★` | `★★★☆☆` |
| Démarrage de l’app (cold start) | `★★★☆☆` | `★★★★★` | `★★★☆☆` |
| Intégration Android (API natives, capteurs, agenda, intents) | `★★★★☆` | `★★★★★` | `★★★☆☆` |
| Vitesse de développement initiale | `★★★★☆` | `★★★☆☆` | `★★★★☆` |
| Maintenabilité long terme | `★★★★☆` | `★★★★★` | `★★★☆☆` |
| Stabilité des dépendances | `★★★★☆` | `★★★★★` | `★★★☆☆` |
| Disponibilité développeurs (marché) | `★★★☆☆` | `★★★★☆` | `★★★★☆` |
| Évolutivité (montée en complexité produit) | `★★★★☆` | `★★★★★` | `★★★☆☆` |
| Taille binaire Android (APK/AAB) | `★★★☆☆` | `★★★★☆` | `★★★☆☆` |
| **Moyenne globale** | **`3.8 / 5` (`★★★★☆`)** | **`4.6 / 5` (`★★★★★`)** | **`3.4 / 5` (`★★★☆☆`)** |

## Lecture rapide par techno

### Kotlin (Android natif)
- **Forces** : meilleures performances globales, intégration Android parfaite, code très solide dans le temps.
- **Faiblesses** : vélocité initiale souvent un peu plus lente qu’un framework cross-platform.
- **Adapté si** : priorité à la qualité Android, fiabilité, maîtrise des APIs natives (Google Agenda, paiements, intents, etc.).

### Flutter
- **Forces** : très bonne vitesse de dev, UI homogène et moderne, bonnes performances.
- **Faiblesses** : intégrations natives parfois plus techniques sur certains plugins spécifiques.
- **Adapté si** : besoin d’aller vite avec une UI très travaillée et possibilité future de multi-plateforme.

### React Native
- **Forces** : bonne productivité si équipe JavaScript/React.
- **Faiblesses** : variabilité de perf selon le pont natif/librairies, maintenance plus sensible aux versions de dépendances.
- **Adapté si** : équipe déjà très orientée React et priorité à la rapidité de mise en place.

## Recommandation pour ton contexte

Pour un projet **Android-only** avec objectif de crédibilité pro et maintien long terme, **Kotlin natif** est le meilleur choix global.

Si tu veux accélérer fortement le time-to-market tout en gardant un bon niveau de qualité, **Flutter** est la meilleure alternative.

## Note sur les fonctionnalités demandées

- Goodies + paiement : faisable dans les 3 stacks (intégration API Stripe/PayPal côté back obligatoire).
- Crowdfunding : simple lien/onglet webview ou navigateur externe, faisable partout.
- Réservation créneaux (Google Agenda) : plus direct en natif Kotlin, faisable aussi en Flutter/React Native via plugins/modules natifs.
