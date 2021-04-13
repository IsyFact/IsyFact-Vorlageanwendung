package de.bund.bva.isyfact.terminfindung.core.erstellung;

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

import java.util.List;

import de.bund.bva.isyfact.terminfindung.common.exception.TerminfindungBusinessException;
import de.bund.bva.isyfact.terminfindung.persistence.entity.Tag;
import de.bund.bva.isyfact.terminfindung.persistence.entity.Terminfindung;

/**
 * Interface der Anwendungskomponente "Erstellung" zur Erstellung von Terminfindungen
 *
 * @author BVA
 */
public interface Erstellung {

    /**
     * Erstellt eine neue Terminfindung.
     *
     * @param organisatorName   Name des Organisators
     * @param veranstaltungName Name der Veranstaltung
     * @param termine           Termine, an denen die Veranstaltung stattfinden könnte
     * @return die neu erstelle Terminfindung.
     * @throws TerminfindungBusinessException falls fachliche Fehler in den Eingabedaten sind.
     */
    Terminfindung erstelleTerminfindung(String organisatorName, String veranstaltungName, List<Tag> termine)
            throws TerminfindungBusinessException;

}
