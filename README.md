# 3A - SBC : Systèmes à Base de Connaissances

* Projet de SBC en 3A à Télécom Nancy
* [https://github.com/AnthonyTraut/SBC/invitations](https://github.com/AnthonyTraut/SBC/invitations)

## Auteurs :

* Yannick PHILIPPE
* Matthieu RAPENNE
* Erwan SCHILD
* Anthony TRAUT

## Sujet :

La visualisation des Linked Open Data est essentielle pour permettre à un utilisateur d'appréhender la structure et les différents liens dans un jeu de données qui lui est inconnu. Une visualisation proposre [1] est construite en détectant les éléments de la TBox les plus fréquemment instanciés (classes et prédicats entre les instances des classes) à partir de requêtes SPARQL sur le SPARQL endpoint d'un jeu de données.
Néanmoins, cette visualisation n'affiche que les éléments de la TBox les plus fréquents. Il pourrait être intéressant d'afficher toute la TBox puis de mettre cette TBox en perspective avec ce qui existe fréquemment dans la ABox. Par exemple, supposons l'existence d'une propriété partOf dont le domain est défini comme étant la classe GeneVariant et le range comme étant la classe Gene. Il serait utile d'afficher cette définition de la TBox puis d'afficher (d'une manière élégante) les véritables classes fréquemment appliquées en domain et en range de cette propriété. Il ne s'agit bien évidemment pas de l'unique possibilité. Il serait intéressant d'exploiter l'ensemble des connaissances proposées par les ontologies : hiérarchies de classes, de propriétés, mappings owl:sameAs, liens de cross-references... L'outil présenté propose également un unique mode d'exploration du jeu de données considéré : à partir de la fréquence d'instanciation des éléments de la TBox. On pourrait également rééchir à d'autres approches : à partir d'une instance particulière, à partir d'un groupe d'instances, à partir d'une propriété, d'un groupe de propriétés, en interaction avec les souhaits d'expansion de l'utilisateur...

## Références :

* [1] Steffen Lohmann, Vincent Link, Eduard Marbach, and Stefan Negru. Extraction and visualization of tbox information from SPARQL endpoints. In Proceedings of the 20th International Conference on Knowledge Engineering and Knowledge Management (EKAW 2016), volume 10024 of LNAI, pages 713{728. Springer, 2016.

* [2] M. Weise, S. Lohmann, F. Haag: LD-VOWL: Extracting and Visualizing Schema Information for Linked Data. Proceedings of the 2nd International Workshop on Visualization and Interaction for Ontologies and Linked Data (VOILA 2016), pp. 120-127, CEUR-WS, vol. 1704, 2016.

* [3] A. Graziosi, A. Di Iorio, F. Poggi, S. Peroni: Customised Visualisations of Linked Open Data. Proceedings of the Third International Workshop on Visualization and Interaction for Ontologies and Linked Data co-located with the 16th International Semantic Web Conference (ISWC 2017), pp. 20-33, CEUR-WS, vol. 1947, 2017.

* Sites présentant une démonstration d'un projet analogue :
   * [http://vowl.visualdataweb.org/ldvowl.html](http://vowl.visualdataweb.org/ldvowl.html)
   * [http://vowl.visualdataweb.org/ldvowl/#/](http://vowl.visualdataweb.org/ldvowl/#/)
   * [http://vowl.visualdataweb.org/webvowl.html](http://vowl.visualdataweb.org/webvowl.html)
   * [http://www.visualdataweb.de/webvowl/](http://www.visualdataweb.de/webvowl/)

## Dépendances du projet :

* apache-jena-3.6.0
   * Utilisé pour effectuer les requêtes SPARQL ;
   * Pas besoin d'ajouter quoi que ce soit pour le fonctionnement du projet.
* jung2-2_0_1
   * Utilisé pour la gestion des graphes et leur visualisation ;
   * Les librairies sont disponibles dans le dossier /jung2-2_0_1 ;
   * Elles sont à importées en tant qu'External JARs au Build Path du projet.
