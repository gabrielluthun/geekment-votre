# Frontend mobile : React Native, Flutter, Kotlin Multiplatform

## React Native : nouvelle architecture et écosystème JavaScript

React Native repose sur JavaScript/TypeScript et React, avec la promesse : **"Learn once, write anywhere"**. Cette approche permet à des équipes web de réutiliser leurs compétences sur mobile.

### Ancienne architecture : le Bridge

Jusqu'à récemment, React Native utilisait le **Bridge** :

- le code JavaScript et le code natif tournaient sur des threads séparés ;
- les échanges passaient par sérialisation/désérialisation JSON ;
- cette couche créait des goulots d'étranglement (listes longues, animations rapides).

### Nouvelle architecture (RN 0.78 -> 0.80)

Entre 2025 et 2026, la nouvelle architecture est devenue le standard, avec trois piliers :

1. **JSI (JavaScript Interface)**  
   Supprime la dépendance au JSON en autorisant des appels synchrones directs vers des fonctions C++ natives.

2. **Fabric Renderer**  
   Nouveau moteur de rendu, synchronisé avec la logique JavaScript. Les interactions rapides (ex. scroll intensif) sont mieux absorbées. Des mesures terrain indiquent un gain de fluidité d'environ **30 %** dans certains scénarios de défilement intensif.

3. **TurboModules**  
   Introduit le **lazy-loading** des modules natifs : ils sont chargés uniquement lorsqu'ils sont appelés, ce qui réduit l'overhead au démarrage et améliore le cold start.

### Typage

Avec React Native 0.80, une **Strict TypeScript API** (opt-in) renforce la robustesse et la prédictibilité du code sur des projets à grande échelle.

---

## Flutter : moteur de rendu autonome et cohérence visuelle

Flutter (Dart) adopte une philosophie opposée à React Native : il ne s'appuie pas sur les composants UI natifs de l'OS, mais **dessine lui-même chaque pixel**. Résultat : une cohérence visuelle quasi identique sur iOS, Android, web et desktop.

### Impeller 2.0 (2026)

Flutter a généralisé **Impeller 2.0**, successeur opérationnel de l'approche historique centrée sur Skia pour le rendu en production :

- limitation des saccades liées à la compilation dynamique de shaders ;
- meilleure stabilité du framerate ;
- animations plus constantes à 60/120 FPS.

### Évolutions Dart 4.x et IA

- Dart 4.x améliore la gestion mémoire, la charge du garbage collector et les workflows AOT.
- Le framework intègre des briques IA (ex. TensorFlow Lite, intégrations Genkit) pour des usages génératifs.

### Compromis

Cette indépendance UI implique d'embarquer le moteur de rendu dans l'application, ce qui augmente la taille du binaire final.

---

## Kotlin Multiplatform (KMP) : partage ciblé et natif

KMP n'est pas d'abord un framework UI ; c'est une technologie de **partage de code**. Le principe : mutualiser la logique métier (données, réseau, cache, état) et garder le spécifique plateforme au niveau natif.

### Modèle expected / actual

Le code commun déclare des contrats `expected`, et chaque plateforme fournit son implémentation `actual`.

- Android : compilation Kotlin classique (JVM/ART).
- iOS : compilation via Kotlin/Native (LLVM) en binaire natif, sans VM intermédiaire.

### Compose Multiplatform 1.8.0 (iOS)

La sortie de la version 1.8.0 a stabilisé Compose Multiplatform pour iOS :

- parité fonctionnelle accrue avec Jetpack Compose ;
- navigation typée et deep linking ;
- support renforcé de l'accessibilité Apple (VoiceOver, AssistiveTouch).

KMP peut ainsi couvrir une stratégie mobile quasi full-stack, tout en laissant la possibilité d'utiliser SwiftUI quand nécessaire.

---

## Évaluation des performances : critères clés

La performance mobile ne se limite pas à la vitesse brute. Elle combine :

- fluidité visuelle (FPS) ;
- consommation CPU/mémoire/batterie ;
- temps de démarrage ;
- taille du binaire distribué.

Selon les tendances mentionnées ici, sur le critère du rendu visuel stable à FPS élevé, **Flutter** et **KMP avec UI native** restent les plus solides.