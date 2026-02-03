# CONTRAINTES DU PROJET - GEEKMENT VÔTRE

## 1. CONTRAINTES TECHNIQUES
------------------------------------------------------------------
* Système d'exploitation cible :
  - Développement exclusif pour Android.
  - Exclusion du déploiement iOS dans le périmètre initial.

* Architecture "Mobile-First" :
  - Conception de l'interface et de l'expérience utilisateur (UX/UI) pensée prioritairement pour le support mobile.
  - Base technique devant faciliter une future refonte du site web (architecture 3-tiers recommandée).

* Interopérabilité (API & Services Tiers) :
  - Google Agenda : Synchronisation requise pour la gestion des créneaux de réservation (MJ à domicile).
  - Modules de Paiement : Intégration de PayPal (choix primaire) et Stripe (choix secondaire en cas d'échec).
  - Crowdfunding : Intégration par lien externe (redirection web).  


## 2. CONTRAINTES FINANCIÈRES
------------------------------------------------------------------
* Budget Store :
  - A ce jour, il n'est pas prévu un déploiement sur l'App Store d'Apple au vu des coûts que cela représente (99€/an).
  - Publication uniquement sur le Google Play Store (frais unique de 25$).

* Coût de maintenance :
  - L'architecture doit rester légère (hébergement backend peu coûteux)  
