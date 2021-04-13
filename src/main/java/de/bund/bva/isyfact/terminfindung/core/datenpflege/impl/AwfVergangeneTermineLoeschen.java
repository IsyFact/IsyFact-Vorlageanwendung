package de.bund.bva.isyfact.terminfindung.core.datenpflege.impl;

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


import java.time.LocalDate;

import de.bund.bva.isyfact.datetime.util.DateTimeUtil;
import de.bund.bva.isyfact.terminfindung.common.exception.TerminfindungBusinessException;
import de.bund.bva.isyfact.terminfindung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.terminfindung.persistence.dao.TerminfindungDao;
import de.bund.bva.isyfact.terminfindung.persistence.entity.Terminfindung;

/**
 * Diese Klasse implementiert den Anwendungsfall "Vergangene Termine löschen"
 *
 * @author msg systems ag
 */

class AwfVergangeneTermineLoeschen {

    private final TerminfindungDao terminfindungDao;

    AwfVergangeneTermineLoeschen(TerminfindungDao terminfindungDao) {
        this.terminfindungDao = terminfindungDao;
    }

    /**
     * Löscht alle Terminfindungen, die abgeschlossen wurden und deren Termine vor dem angegebenen Stichtag
     * stattgefunden haben.
     *
     * @param stichtag Stichtag
     * @return Anzahl der gelöschten Terminfindungen.
     * @throws TerminfindungBusinessException falls das Datum ungültig ist.
     */
    int loescheVergangeneTerminfindungen(LocalDate stichtag) throws TerminfindungBusinessException {

        if (stichtag == null) {
            throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_UNGUELTIG, "stichtag", "null");
        }
        if (stichtag.isAfter(DateTimeUtil.localDateNow())) {
            throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_DATUM_ZUKUNFT, "stichtag", stichtag.toString());
        }

        int anzahlGeloescht = 0;
        for (Terminfindung tf : terminfindungDao.sucheVor(stichtag)) {
            terminfindungDao.loesche(tf);
            anzahlGeloescht++;
        }

        return anzahlGeloescht;
    }

}
