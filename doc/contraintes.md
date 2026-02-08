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


## 3. CONTRAINTES ERGONOMIQUES & GRAPHIQUES (DESIGN)
------------------------------------------------------------------
* Charte Graphique Imposée :
  - Respect strict de l'identité visuelle existante (Logo, Font, Couleurs) issue du site "geekementvotre.fr".
  - Cohérence visuelle obligatoire entre l'application et la marque actuelle.

* Niveau de qualité (Image de Marque) :
  - Objectif "Crédibilisation" : L'esthétique de l'application doit être supérieure à celle du site actuel (qualifié de "fait à la va-vite").
  - Rendu professionnel exigé pour rassurer les clients potentiels.

* Accessibilité Cible :
  - Interface intuitive adaptée à la cible "Passionnés de JDR / Pop-culture".
  - Navigation fluide pour faciliter la prise de contact et l'achat.   
  
4. CONTRAINTES FONCTIONNELLES
------------------------------------------------------------------
* Redondance des paiements (Failover) :
  - Le système doit proposer PayPal par défaut.
  - Le système doit basculer sur Stripe uniquement si PayPal renvoie une erreur ou est indisponible.

* Redirection Crowdfunding :
  - La participation au financement participatif ne doit pas être native (in-app).
  - L'application doit gérer une redirection propre vers la plateforme externe (future cagnotte) via le navigateur du téléphone.

* Gestion des stocks (Flux Tendu) :
  - Le système doit gérer des produits sans stock physique immédiat (commande fournisseur déclenchée par l'achat).  
