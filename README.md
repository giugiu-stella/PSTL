# PSTL
Projet 421

# RAPPORT DU PROJET =
https://fr.overleaf.com/project/63f4e8aa9aa9dd814da6559c

## Organisation
code/sans_rejets: Le jeu avec un générateur sans rejet d'information.
 - Game.java: Le jeu 
 - Bit_aleatoire_sans_rejet.java: Fichier qui génère un output.txt grace au générateur.
 - partie1_avec_rejet: Fichier qui calcule la graine (seed) et fait la prédiction.



code/avec_rejets:Le jeu avec un générateur avec rejet d'information.
- Game.java: Le jeu
- Construction.py: Fichier qui calcule la liste des couples.
- Bit_aleatoire_avec_rejet.java: Fichier qui génère un output.txt grace au générateur.
- partie1_avec_rejet.py: Fichier qui calcule la graine (seed) et  fait la prédiction.

Les dossiers dice et jeu dans code correspondent au premier code de notre version du jeu, que nous avons décidé de ne pas utiliser.

## Pour lancer le projet: 
Dans le sous répértoire code/sans_rejets:
```
javac ./Bit_aleatoire_sans_rejet.java
```
```
java  Bit_aleatoire_sans_rejet
```

```
py ./partie1_sans_rejet.py 
```
Pour lancer le jeu :
```
javac ./Game.java.
java  Game
```
Dans le sous répértoire code/avec_rejets:
```
javac ./Bit_aleatoire_avec_rejet.java
```
```
java Bit_aleatoire_avec_rejet
```

```
pypy3 ./partie1_avec_rejet.py 
```
Pour lancer le jeu :
```
javac ./Game.java.
java  Game
```


## Authors

- Lina Azerouk 
- Carla Giuliani
