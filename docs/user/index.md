# ccc-service

Der ccc-service dient dazu, eine Kommunikation 
zwischen GIS und Fachapplikation zu erm�glichen.

Der �Smoketest� um im Betrieb die Verf�gbarkeit des ccc-services zu testen, ist [hier]((probetool.md) beschrieben.

## System Anforderungen
Um die aktuelle Version vom ccc-service auszuf�hren, muss 
 - docker, Version 18.0 oder neuer, installiert sein.

Die docker-Software kann auf der Website http://www.docker.com/ gratis bezogen werden.

## Ausf�hren
Um den ccc-service auszuf�hren, geben Sie auf der Kommandozeile folgendes Kommando ein:

    docker run -d -p 8081:8080 --name ccctest sogis/ccc-service

Der ccc-service ist nun unter der URL ws://localhost:8081/ccc-service ansprechbar.
    
Um die Logs des ccc-service anzusehen, geben Sie auf der Kommandozeile folgendes Kommando ein:

    docker logs ccctest
    
Um den ccc-service zu beenden, geben Sie auf der Kommandozeile folgende Kommandos ein:

    docker kill ccctest
    docker rm ccctest

Um den ccc-service mit ausf�hrlicherem Log auszuf�hren, geben Sie auf der Kommandozeile folgendes Kommando ein:

    docker run -d -p 8080:8080 -e CCC_DEBUG=1 --name ccctest sogis/ccc-service
    
Der ccc-service unterst�tz zus�tzlich die folgenden Umgebungsvariablen:

Variable           | Beschreibung
-------------------|----------------
CCC_MAX_INACTIVITY | Maximal zul�ssige Zeit ohne Meldungsaustausch in Sekunden (Default: 2h)
CCC_MAX_PAIRING    | Maximal zul�ssige Zeit zwischen GIS- und Fachapplikations-Verbindungsaufbau in Sekunden (Default: 60s)

