# 0.11.0
- `IFS-1116`: isyfact-standards Version auf 1.10.0 gesetzt.
- `IF-1016`: Behebung von SpotBugs-Fehlern bei der Datumsvalidierung

# 0.10.0
- `IFS-650`: isyfact-standards Version auf 1.9.0 gesetzt.
    * Isy Style 4.4.0 und Isy Web 4.10.0

# 0.9.1
- `IFS-411`: Javadoc Kommentare angepasst, damit nur die Firma angezeigt wird
- `IFS-650`: isyfact-standards Version 1.8.3

# 0.9.0
- `IFE-171`: Verwendung des AdministrationWatchdogTasks aus isy-ueberwachung
    * Anhebung von isyfact-standards und isyfact-bom auf 1.8.1.

# 0.8.0
- `IFE-132`: Anhebung von isyfact-standards und isyfact-bom auf 1.8.0. 
    * Anpassung von dozer an den neuen Namespace. 
    * Update spring-test-dbunit auf 1.3.0
    * Hibernate-entitymanager entfernt, da jetzt Teil von hibernate-core. 
    * Tidy-maven-plugin entfernt, da Teil von IsyFact 1.8.0.
    * Korrigiert falschen Paketpfad für 'ZonedDateTimeAttributeConverter' in persistence.xml 
    * Änderung des Namespaces (Group-ID/Paketnamen) von "de.msg" zu "de.bund.bva.isyfact".
    * Annotiert @WebAppConfiguration in 'TestJmxUeberwachung', da neuer HilfeController in isy-web den ServletContext benötigt

# 0.7.2
- `IFS-458`: Tidy-Plugin eingebunden, so dass immer ein Check ausgeführt wird. Alle pom.xml Dateien mit tidy:pom formatiert und getestet.