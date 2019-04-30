# Ferienpass

[![Build Status](https://travis-ci.org/digital-bauhaus/Ferienpass.svg?branch=master)](https://travis-ci.org/digital-bauhaus/Ferienpass)
[![License](http://img.shields.io/:license-mit-blue.svg)](https://github.com/digital-bauhaus/Ferienpass-Anmeldung/blob/master/LICENSE)

### Historie

Dieses Projekt führt im Rahmen eines Hackatons die Ergebnisse eines Studentenprojekts weiter. Das Projekt fand innerhalb eines Projektseminars an der [Bauhaus-Universität Weimar](https://www.uni-weimar.de/de/medien/professuren/intelligente-softwaresysteme/) von 2017-10 bis 2018-04 statt. Das Ziel war die Digitalisierung des Anmeldeprozesses für Kinder der Stadt Weimar in Thüringen für Sommerferien-Aktivitäten im Rahmen des gemeinnützigen [Ferienpass-Projektes](http://www.ferienpass-weimar.de/).

> Über das Projekt wurde mehrfach berichtet: Lokale Presse [Thüringer Allgemeine 2018-01-17](http://www.thueringer-allgemeine.de/web/zgt/suche/detail/-/specific/Lange-Schlange-im-Kinderbuero-war-der-Ausloeser-168601916) & [Thüringische Landeszeitung 2018-01-17](http://weimar.tlz.de/web/weimar/startseite/detail/-/specific/Lange-Schlange-im-Kinderbuero-war-der-Ausloeser-168601916) & [BAUHAUS.JOURNAL ONLINE 2018-01-16](https://www.uni-weimar.de/de/universitaet/aktuell/bauhausjournal-online/titel/projektpraesentation-medieninformatik-studierende-entwickeln-online-anmeldung-fuer-weimarer-ferienpas-1/) & [Focus Online](https://www.focus.de/regional/thueringen/bauhaus-universitaet-weimar-meldung-vom-16-01-2018_id_8309726.html) & [codecentric.de](https://www.codecentric.de/2018/01/17/schoenere-ferien-dank-digitalisierung/) & [final celebration, Focus Online](https://www.focus.de/regional/thueringen/stadt-weimar-dank-an-die-foerderer-des-ferienpass-weimar-2018_id_9230231.html)

Die Ergebnisse des Projektes sind in den folgenden zwei Microservices abgebildet:

https://github.com/digital-bauhaus/Ferienpass-Anmeldung

https://github.com/digital-bauhaus/Ferienpass-Admin


Das vorliegende Projekt möchte die Ergebnisse des Studenten-Projektes, dass in einer Alpha-Phase 2018 getestet wurde, nun produktionsreif machen und in 2019 den Kindern zur Verfügung stellen.



### Aufbau

Die bisherige Microservice-Struktur wird zugunsten einer vereinfachten Weiterentwicklung und Wartung aufgegeben und in einen Mini-Monolithen bzw. Microlithen überführt.

Dabei steht die Anmeldungsseite direkt auf der Startseite zur Verfügung (lokal http://localhost:8088/#/) , die Administrationsfunktionen liegen tiefer und finden sich ab http://localhost:8088/#/login



    +-------------------+   +--------------------+
    |                   |   |                    |
    |                   |   |                    |
    |                   |   |                    |
    |     Anmeldung     |   |     Verwaltung     |
    |     (Vue.js)      |   |      (Vue.js)      |
    |                   |   |                    |
    |                   |   |                    |
    +-------------------+   +--------------------+
              |                       |
    +---------v-----------------------v----------+
    |                                            |
    |                                            |
    |                                            |
    |      Spring Boot Backend (REST API)        |
    |                                            |
    |                                            |
    |                                            |
    +--------------------------------------------+
                          |
                          v
            +---------------------------+
            |                           |
            |    Postgres-DB            |
            |    (lokal H2 in-memory)   |
            |                           |
            +---------------------------+


### Continuous Integration and Deployment

Tests werden automatisch bei jedem Push auf den master branch ausgeführt und es finded ein automatischer Deploy auf Heroku statt, wenn der TravisCI build erfolgreich durchgelaufen ist. Die Anwendung kann unter der folgendne URL aufgerufen werden:

https://ferienpass.herokuapp.com/#/