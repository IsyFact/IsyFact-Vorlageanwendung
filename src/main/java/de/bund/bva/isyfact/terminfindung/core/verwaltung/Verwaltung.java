package de.bund.bva.isyfact.terminfindung.core.verwaltung;

/*
 * #%L
 * Terminfindung
 * %%
 * Copyright (C) 2015 - 2016 Bundesverwaltungsamt (BVA), msg systems ag
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import de.bund.bva.isyfact.terminfindung.common.exception.TerminfindungBusinessException;
import de.bund.bva.isyfact.terminfindung.persistence.entity.Terminfindung;
import de.bund.bva.isyfact.terminfindung.persistence.entity.Zeitraum;

import java.util.List;
import java.util.UUID;

/**
 * Interface der Anwendungskomponente "Verwaltung" zur Verwaltung von Terminfindungen
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
public interface Verwaltung {

    /**
     * Liest eine Terminfindung anhand ihrer Referenz-ID.
     *
     * @param terminfindung_ref Referenz-ID der Terminfindung
     * @throws TerminfindungBusinessException wenn es keine Terminfindung mit dieser Referenz-ID gibt.
     */
    Terminfindung leseTerminfindung(UUID terminfindung_ref) throws TerminfindungBusinessException; 
    
    List<Terminfindung> leseAlleTerminfindungen();

    void loescheZeitraeume(Terminfindung terminfindung, List<Zeitraum> zeitraumList);

    void setzeVeranstaltungstermin(Terminfindung terminfindung, long zeitraumNr) throws TerminfindungBusinessException;
    
    void aktualisiereTerminfindung(Terminfindung terminfindung, String organisatorName, String veranstaltungName);

}
