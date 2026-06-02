## Application mobile (globalité)

**RG1**: L'application mobile **doit** être développée pour une interface Android **uniquement**    
**RG2**: L'interface utilisateur **doit** privilégier une approche Mobile-First  
**RG3**: L'accès aux fonctionnalités d'achat de produits ne **doit** **PAS** imposer la création d'un compte  
**RG4**: La saisie d'un email et d'une adresse de facturation reste **obligatoire** lors de l'achat via un formulaire  
**RG5**: L'application **doit** contenir une section présentant l'activité du client  
**RG6**: L'application **doit** contenir une section présentant l'expérience passée du client  
**RG7**: L'application **doit** contenir le logo de l'entreprise du client  
**RG8**: L'application **doit** contenir des **onglets** sur le footer de chacune des pages   
**RG9**: Chaque onglet de l'application **doit** contenir un intitulé   

## Boutique de "goodies"  

**RG10** : La boutique **doit** présenter les produits phares uniquement  
**RG11** : Une dizaine de produits **maximum** devront être présentés  
**RG12** : Chaque produit **doit** inclure la photo, le titre, une description et un prix  
**RG13** : Le titre d'un produit devra tenir en quelques mots  
**RG14** : La description d'un produit devra être rédigée d'une longueur de 5 lignes **maximum**   
**RG15** : Le prix d'un produit devra **toujours** afficher le tarif en Euros (€)  
**RG16** : Le paiement d'une commande devra s'effectuer via PayPal  
**RG17** : En cas d'indisponibilité de PayPal, une alternative de paiement devra être proposée (Stripe)  
**RG18** : Sur PayPal, une commande ne pourra **pas** être honorée si les renseignements demeurent incomplètes   
**RG19** : Le numéro de carte bancaire ne devra être stockée nulle part  

## Partie Jeu de Rôle (JDR)  
### Réservation de partie  

**RG20** : Le JDR devra avoir un onglet **dédié** au sein de l'application  
**RG21** : Le présentateur du JDR est appelé "MJ", ou "Maître du Jeu"  
**RG22** : Cet onglet devra présenter le JDR de façon détaillée   
**RG23** : Cet onglet devra contenir un **bouton** d'action  
**RG24** : Ce bouton devra **rediriger** vers un formulaire de réservation  
**RG25** : Ce formulaire permettra aux joueurs de réserver un créneau pour leur future partie  
**RG26** : Une réservation **doit** être impossible pour une date / heure passée  
**RG27** : La réservation permet d'indiquer le nombre de participants (1 à 99 joueurs)  
**RG28** : Les futurs joueurs devront indiquer leur niveau d'expérience dans ce formulaire  
**RG29** : Le MJ pourra consulter une synthèse du formulaire sur une plateforme dédiée  

### Crowdfunding  
**RG30** : L'application **doit** contenir un onglet ou une page **spécifiquement** dédiée au crowdfunding du JDR  
**RG31** : Cette section **doit** présenter le contexte narratif et l'univers du JDR  
**RG32** : Cette section **doit** comporter des visuels / illustrations représentatif du JDR  
**RG33** : Cette section **doit** comporter un bouton d'appel à l'action bien visible  
**RG34** : Le bouton **doit** renvoyer vers une page de donation / financement  
**RG35** : L'entièreté de la section devra disparaître / remplacée lors de la **fin** de la campagne de financement  

## Validations et Sécurité des données

**RG36** : Le nom et le prénom du client **doivent** obligatoirement commencer par une majuscule  
**RG37** : Le numéro de téléphone **doit** respecter le format français *(10 chiffres commençant par 0 ou format international +33 avec 11 chiffres au total)*  
**RG38** : Le code postal **doit** être composé de 5 chiffres et correspondre aux départements de France métropolitaine (01 à 95)  
**RG39** : L'adresse (rue) **doit** obligatoirement commencer par un type de voie reconnu *(Rue, Avenue, Boulevard, etc.)*  
**RG40** : Si l'option "À domicile" est activée, l'adresse de la session est automatiquement synchronisée avec l'adresse de facturation du client  
**RG41** : Tout message envoyé via les formulaires (contact ou réservation) est filtré contre le langage offensant  
**RG42** : Après chaque soumission valide, un e-mail de confirmation est envoyé au client et une notification est transmise à l'administrateur
**RG43** : Le bouton de soumission des formulaires reste **désactivé** tant que **l'ensemble** des critères de validation *(format, champs obligatoires, filtre offensant)* n'est pas rempli
**RG44** : Les champs numériques (*Téléphone, Code Postal, Nb Joueurs, Numéro de rue)* sont **bridés à la saisie** pour empêcher techniquement le dépassement des limites
**RG45** : La validation du nombre de joueurs accepte les formats à **1 ou 2 chiffres** *(ex: "5" est valide, pas besoin de "05")*

