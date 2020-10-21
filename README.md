# Ferienpass

[![Build Status](https://travis-ci.org/digital-bauhaus/Ferienpass.svg?branch=master)](https://travis-ci.org/digital-bauhaus/Ferienpass)
[![Coverage Status](https://coveralls.io/repos/github/digital-bauhaus/Ferienpass/badge.svg?branch=master)](https://coveralls.io/github/digital-bauhaus/Ferienpass?branch=master)
[![License](http://img.shields.io/:license-mit-blue.svg)](https://github.com/digital-bauhaus/Ferienpass-Anmeldung/blob/master/LICENSE)

Dieses Projekt beinhaltet alle Informationen und den Quellcode rund um die Digitalisierung des Anmeldeprozesses für Kinder der Stadt Weimar in Thüringen für Sommerferien-Aktivitäten im Rahmen des gemeinnützigen [Ferienpass-Projektes](http://www.ferienpass-weimar.de/).

## Historie

Das Projekt wurde ursprünglich von Studierenden der [Bauhaus-Universität Weimar](https://www.uni-weimar.de/de/medien/professuren/intelligente-softwaresysteme/) im Rahmen eines Projektseminars von 10/2017 bis 04/2018 entwickelt.

Die Ergebnisse dieser Arbeiten sind in den folgenden zwei Microservices abgebildet:

https://github.com/digital-bauhaus/Ferienpass-Anmeldung

https://github.com/digital-bauhaus/Ferienpass-Admin

Das vorliegende Projekt dient dazu die Ergebnisse des Studenten-Projektes, welches in einer Alpha-Phase 2018 getestet wurde, produktionsreif zu machen.

Dazu steht der gesamte Quellcode als OpenSource zur Verfügung und bietet somit allen Interessierten die Möglichkeit mitzuentwickeln.
So geschehen z.B. im Mai 2019 im Rahmen eins [Hackathons](https://www.meetup.com/hackthde/events/258489906/).

2020 wurde das Projekt erstmals erfolgreich für die digitale Anmeldung zum Ferienpass eingesetzt.

> Über das Projekt wurde mehrfach berichtet:
> [BAUHAUS.JOURNAL ONLINE 2018-01-16](https://www.uni-weimar.de/de/universitaet/aktuell/bauhausjournal-online/archiv-suche/titel/projektpraesentation-medieninformatik-studierende-entwickeln-online-anmeldung-fuer-weimarer-ferienpas-1/) &
> [Thüringer Allgemeine 2018-01-17](http://www.thueringer-allgemeine.de/web/zgt/suche/detail/-/specific/Lange-Schlange-im-Kinderbuero-war-der-Ausloeser-168601916) &
> [Thüringische Landeszeitung 2018-01-17](http://weimar.tlz.de/web/weimar/startseite/detail/-/specific/Lange-Schlange-im-Kinderbuero-war-der-Ausloeser-168601916) &
> [Focus Online](https://www.focus.de/regional/thueringen/bauhaus-universitaet-weimar-meldung-vom-16-01-2018_id_8309726.html) &
> [codecentric.de](https://www.codecentric.de/2018/01/17/schoenere-ferien-dank-digitalisierung/) &
> [final celebration, Focus Online](https://www.focus.de/regional/thueringen/stadt-weimar-dank-an-die-foerderer-des-ferienpass-weimar-2018_id_9230231.html) &
> [Thüringer Allgemeine 2018-07-14](https://www.thueringer-allgemeine.de/leben/vermischtes/ferienpass-bietet-jede-menge-spass-fuer-175-maedchen-und-jungen-id224383479.html) &
> [Thüringer Allgemeine 2020-06-16](https://www.thueringer-allgemeine.de/regionen/weimar/ferienspass-mit-dem-ferienpass-id229327108.html )

## Nutzung

Die Anmeldungsseite steht direkt auf der Startseite zur Verfügung (lokal http://localhost:8088/), die Administrationsfunktionen liegen tiefer und finden sich ab http://localhost:8088/login.

#### Login

Der Login unter https://ferienpass.herokuapp.com/login ist nun abgesichert - die Credentials werden lokal über die [application.properties](backend/src/main/resources/application.properties) konfiguriert, im PR bzw. Produktivdeployment über Umgebungsvariablen in Heroku: 

```
# application.properties
spring.security.user.name=test
spring.security.user.password=foo

# Heroku Environment Variables
SPRING_SECURITY_USER_NAME=xyz
SPRING_SECURITY_USER_PASSWORD=xyz
```

#### Bestätigungsmails

Die Mails für die erfolgreiche Anmeldung werden nun mit Hilfe des [Heroku-Addons Sendgrid](https://elements.heroku.com/addons/sendgrid) verschickt. Im genutzten Free-Tier sind 12.000 freie Mails inbegriffen pro Monat.

Der Test dafür benötigt lokal eine manuell zu setzende Umgebungsvariable `SENDGRID_API_KEY=korrekterKey` mit dem korrekten Key (den Key am besten aus der Heroku-Configvar beziehen!) - z.B. innerhalb der Run Configurations der IDE.

Innerhalb von TravisCI wird auch die Environment Variable benötigt. 

Der Inhalt der Mail wird über die Datei [mailtext.txt](backend/src/main/resources/mail/mailtext.txt) beschrieben.


# Contribute

## Aufbau

Die bisherige Microservice-Struktur wird zugunsten einer vereinfachten Weiterentwicklung und Wartung aufgegeben und in einen Mini-Monolithen bzw. Microlithen überführt.

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

Die beiden fachlichen Frontends sind dabei als gemeinsames Vue-Projekt umgesetzt: Modul/Unterordner [frontend](frontend).

Das Spring Boot Backend befindet sich im Modul/Unterordner [backend](backend).

## Prerequisites

* Java
* npm (optional, für lokale Frontend-Entwicklung)

MacOS

```console
> brew cask install java
> brew install npm
```

Windows

```console
> choco install jdk
> choco install npm
```

Linux

```console
> apt-get install jdk
> apt-get install npm
```

### Local Setup

Wir nutzen den [maven-wrapper](https://github.com/takari/maven-wrapper).
Dadurch wird keine eigene Maven-Installation benötigt.

Unter Linux und Mac kann der maven-wrapper so eingesetzt werden
```
./mvnw <maven command here>
```

Unter Windows erfolgt der Aufruf mit
```
mvnw.cmd <maven command here>
```

Im Folgenden gehen wir immer von Aufrufen unter Linux aus.

#### build project

Der folgende Befehl baut das `frontend`-Projekt und kopiert die dabei entstehenden Artefakte in den `resources`-Ordner des `backend`-Projektes. Dadurch kann es direkt vom eingebetteten Tomcat-Server des Spring Boot Backends ausgeliefert werden. 

```console
> .mvnw clean install
```

#### run project

Backend-Server starten

```console
> .mvnw --projects backend spring-boot:run
```

Dieser läuft dann auf Port 8088 und stellt dort die REST-API und die Frontends bereit.

REST-API:

`http://localhost:8088/api`

`http://localhost:8088/swagger-ui.html`

Anmeldungs-Frontend:

`http://localhost:8088/#/`

Verwaltungs-Frontend:

`http://localhost:8088/#/Veranstaltungen/`

#### local frontend development

Für die lokale Entwicklung des Frontends ist es einfacher die entsprechenden Targets aus der [package.json](frontend/package.json) des `frontend`-Moduls zu verwenden.

Dafür muss zuerst (wie oben beschrieben) der Backend-Server gestartet werden, damit die REST-API zur Verfügung steht.

Danach kann mit folgendem Befehl ein lokaler Entwicklungsserver auf Port 8080 gestartet werden, der u.a. Hot-Reload unterstützt.
```
cd frontend
npm run serve
```

Anmeldungs-Frontend:

`http://localhost:8080/#/`

Verwaltungs-Frontend:

`http://localhost:8080/#/Veranstaltungen/`

Weitere Informationen in der [README](frontend/README.md) des `frontend`-Moduls.


### Continuous Integration and Deployment

Tests werden automatisch bei jedem Push auf den Feature-Branches oder den master durch [TravisCI](https://travis-ci.org/digital-bauhaus/Ferienpass) ausgeführt und es finded ein automatischer Deploy auf Heroku statt, wenn der TravisCI build erfolgreich durchgelaufen ist.

Entwickelt werden soll mit Hilfe von Feature-Branches und Pull-Requests - der master-Branch ist als "Produktions-ready" immer baufähig zu halten.
 

#### Dev-Workflow

* __Clone:__ lokal das Repository clonen per `git clone https://github.com/digital-bauhaus/Ferienpass.git`
* __No Merge Commits:__ lokal das Erstellen von Merge-Commits unterbinden mit der Einstellung: `git config --global pull.rebase preserve`
* __Focus on Issues:__ auf [GitHub ein Issue](https://github.com/digital-bauhaus/Ferienpass/issues) auswählen oder neu erstellen
* __Create Branch:__ dann einen neuen Branch erstellen nach dem Muster `feature/#issueNummer-issue-titel`, z.B. `feature/#2-kostenplichtige-anmeldung`
* lokal pullen und entwickeln 
* __Do Tests run?__ Vor dem Commit sicherstellen, dass alle Tests laufen und die Anwendung baut
* __Commit message!__ Beim Commit darauf achten, dass der Kommentar die GitHub-Issue-Nummer enthält, z.B. `#4 Neuer Button erstellt` 
* Pushen


#### Feature ready? --> Pull Request!

__Wenn dein Feature fertig ist:__

Pull Request auf GitHub erstellen

![create-pull-request](docs/create-pull-request.png)

__Review App:__

_Hinweis: Aktuell sind die Review-Apps deaktiviert, da [Dependabot](https://github.com/dependabot) sehr viele PRs für Version-Updates erzeugt und es scheinbar nicht möglich ist Review-Apps in Heroku spezifisch automatisch zu erstellen (siehe https://github.com/digital-bauhaus/Ferienpass/issues/175)._

[Heroku](https://dashboard.heroku.com/pipelines/6d86397b-7093-4252-b978-2f57b25e5620) erstellt nun automatisch eine Review-App für diesen Pull-Request (Access für die Pipeline bitte bei [jonashackt](https://github.com/jonashackt) anfragen):

![heroku-pipeline](docs/heroku-pipeline.png)

Heroku erstellt eine eigene URL für die Anwendung, unter der sie getestet werden kann. Die URL kann über `Open App in browser` über den kleinen Button neben der App geöffnet werden:

![heroku-review-app-link](docs/heroku-review-app-link.png)

__PR prüfen__

Auf GitHub werden alle Commits, die Builds sowie die Heroku-Deployments vollständig im Pull Request dargestellt:

![github-pr](docs/github-pr.png)

Wenn alles passt, kann das Featuer in den master-Branch gemergt werden per `Merge pull request`. __Danach landed der Stand automatisch auf Staging bzw. Produktion!__

__Delete Feature-Branch:__

Direkt auf GitHub im Pull Request sollte man den Feature Branch gleich noch löschen! Dann wird auch auf Heroku die Review App wieder weggeräumt (und verursacht keine Kosten!).

__local master FTW:__

Lokal nun wieder auf den master-Branch wechseln und das Projekt neu pullen, der alte Feature-Branch sollte nun auch lokal gelöscht sein.

#### Staging / Production

Die pre-produktive / produktive Anwendung kann unter der folgenden URL aufgerufen werden:

https://ferienpass.herokuapp.com/#/
