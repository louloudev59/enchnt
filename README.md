# Enchnt - Plugin d'Enchantements pour Minecraft

Enchnt est un plugin Spigot qui ajoute de nouveaux enchantements innovants à Minecraft, enrichissant l'expérience de jeu avec des mécaniques uniques et amusantes.

## Fonctionnalités

- Plus de 20 nouveaux enchantements pour les armes, armures, outils et boucliers
- Configuration personnalisable pour chaque enchantement
- Compatible avec les versions de Minecraft 1.12 à 1.20
- Système de gestion des conflits entre enchantements

## Installation

1. Téléchargez la dernière version du plugin depuis la section Releases
2. Placez le fichier .jar dans le dossier `plugins` de votre serveur
3. Redémarrez votre serveur
4. Le plugin créera automatiquement un fichier de configuration

## Configuration

Le fichier `config.yml` permet de personnaliser :
- L'activation/désactivation de chaque enchantement
- Les niveaux maximum
- Les effets spécifiques (dégâts, durée, rayon, etc.)
- Les probabilités d'apparition

## Commandes

- `/enchnt` - Commande principale du plugin
- `/enchnthelp` - Affiche l'aide sur les enchantements disponibles
- `/enchntadmin` - Interface d'administration des enchantements (nécessite la permission `enchnt.admin`)
  - `/enchntadmin reload` - Recharge la configuration
  - `/enchntadmin give <joueur> <enchantement> [niveau]` - Donne un enchantement à un joueur

## Enchantements

### Armes
- **Tir Rapide** - Tire plusieurs flèches en succession rapide
- **Dart de Corde** - Attache une corde invisible à l'ennemi
- **Tir Éparpillé** - Endommage les entités proches de la cible
- **Feu Follet** - Les entités tuées explosent
- **Exécuteur** - Chance de faire tomber la tête des mobs
- **Toxique** - Réduit la régénération des cibles
- **Barbare** - Augmente les dégâts d'attaque
- **Écrasant** - Dégâts bonus contre les armures lourdes
- **Perforant** - Dégâts bonus contre les armures légères

### Outils
- **Excavation** - Mine plusieurs blocs simultanément
- **Déforestation** - Coupe un arbre entier en un clic
- **Récolte** - Récolte et replante automatiquement
- **Affinité** - Réparation avec les minerais correspondants

### Armures
- **Vitalité** - Améliore les soins reçus
- **Vigoureux** - Augmente les points de vie maximum
- **Stable** - Réduit le recul
- **Plates** - Valeur d'armure supplémentaire
- **Peau de Basalte** - Résistance au feu
- **Marcheur de Lave** - Marche sur la lave
- **Secoueur de Terre** - Immunité aux chutes
- **Protection** - Chance de dévier les projectiles
- **Métabolisme** - Régénération de la faim

### Boucliers
- **Neutraliseur** - Bloque les effets des potions
- **Onde de Choc** - Repousse les mobs environnants

### Général
- **Télékinésie** - Les objets vont directement dans l'inventaire
- **Lié à l'Âme** - Les objets ne sont pas perdus à la mort
- **Rajeunissement** - Régénération de la durabilité
- **Siphon** - Plus d'EXP des entités tuées

## Développement

Le plugin est développé en Java utilisant l'API Spigot. Pour contribuer :

1. Clonez le dépôt
2. Importez le projet dans votre IDE
3. Installez les dépendances avec Maven
4. Modifiez le code
5. Soumettez une pull request

## Licence

Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails. 