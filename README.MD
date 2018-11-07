### SupRails - Client SOAP

Ce projet permet de lister les voyages et de reserver un voyage sur l'application suprails via ses web services SOAP.

### Pré-requis

* JDK 1.7 minim
* Avoir un supRails qui tourne sur un serveur (local ou distant)

### Génération des classes de suprails via le WSDL

* Créer un dossier generated à la racine de votre projet.
* Utilisez la commande (faire pointer les dossier src et bin dans le dossier generated créé ci dessus) : 

`wsimport -s <path_to_a_folder>/src -d <path_to_a_folder>/bin <path_to_your_wsdl> `

* Exemple :

`wsimport -s /home/git/Suprails-SaopClient/generated/src -d /home/git/Suprails-SaopClient/generated/bin http://localhost:8080/SupRails_war_exploded/soap/trips?WSDL`

* Cela va générer des classes java dans le dossier src. Cliquez sur ce dossier src et Mark directory as > Generated Source Root
* Personnalisez les variables : TRIP_WSDL et TRIP_QNAME en fonction de votre projet.
* Lancez le main