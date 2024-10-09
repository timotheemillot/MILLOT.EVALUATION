# Application de Gestion des Personnages et Lieux de Rick et Morty

## Description

Cette application permet de gérer et afficher des informations sur des personnages et des lieux de l'univers de Rick et Morty. Elle utilise Kotlin, Java et Gradle pour le développement, et s'appuie sur des bibliothèques comme Jetpack Compose pour l'interface utilisateur et Koin pour l'injection de dépendances.

## Fonctionnalités

- Affichage de la liste des personnages
- Affichage des détails d'un personnage
- Affichage de la liste des lieux
- Affichage des détails d'un lieu
- Navigation entre les écrans

## Structure du Projet

- `features/characters`: Contient les fonctionnalités liées aux personnages.
  - `list`: Affichage de la liste des personnages.
  - `details`: Affichage des détails d'un personnage.
- `features/locations`: Contient les fonctionnalités liées aux lieux.
  - `list`: Affichage de la liste des lieux.
  - `details`: Affichage des détails d'un lieu.
- `core/data`: Contient les implémentations des repositories et les objets de données.
- `core/domain`: Contient les modèles de domaine et les interfaces des repositories.

## Prérequis

- Android Studio Koala Feature Drop | 2024.1.2
- JDK 11 ou supérieur
- Connexion Internet pour les appels API

## Dépendances

- Jetpack Compose
- Koin
- Coil
- Realm
- Retrofit

## Auteurs

- Mathieu Perroud
- Timothée Millot