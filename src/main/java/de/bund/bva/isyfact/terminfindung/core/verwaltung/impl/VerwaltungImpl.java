package de.bund.bva.isyfact.terminfindung.core.verwaltung.impl;

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
import java.util.UUID;

import de.bund.bva.isyfact.terminfindung.common.exception.TerminfindungBusinessException;
import de.bund.bva.isyfact.terminfindung.common.konstanten.FehlerSchluessel;
import de.bund.bva.isyfact.terminfindung.core.verwaltung.Verwaltung;
import de.bund.bva.isyfact.terminfindung.persistence.dao.TerminfindungDao;
import de.bund.bva.isyfact.terminfindung.persistence.entity.Terminfindung;
import de.bund.bva.isyfact.terminfindung.persistence.entity.Zeitraum;

/**
 * Interface der Anwendungskomponente "Erstellung" zur Erstellung von Terminfindungen
 *
 * @author msg systems ag
 */

public class VerwaltungImpl implements Verwaltung {

    private final AwfTerminfindungAbschliessen awfTerminfindungAbschliessen;

    private final AwfTermineLoeschen awfTermineLoeschen;

    private final AwfAktualisiereTerminfindung awfAktualisiereTerminfindung;

    private final TerminfindungDao terminfindungDao;


    public VerwaltungImpl(TerminfindungDao terminfindungDao) {
        awfTerminfindungAbschliessen = new AwfTerminfindungAbschliessen(terminfindungDao);
        awfTermineLoeschen = new AwfTermineLoeschen(terminfindungDao);
        awfAktualisiereTerminfindung = new AwfAktualisiereTerminfindung(terminfindungDao);
        this.terminfindungDao = terminfindungDao;
    }

    @Override
    public Terminfindung leseTerminfindung(UUID terminfindung_ref) throws TerminfindungBusinessException {
        Terminfindung tf = terminfindungDao.sucheMitReferenz(terminfindung_ref.toString());
        if (tf == null) {
            throw new TerminfindungBusinessException(FehlerSchluessel.MSG_TERMINFINDUNG_NICHT_GEFUNDEN, terminfindung_ref.toString());
        }
        return tf;
    }

    @Override
    public List<Terminfindung> leseAlleTerminfindungen() {
        return terminfindungDao.findeAlle();
    }

    @Override
    public void setzeVeranstaltungstermin(Terminfindung terminfindung, long zeitraumNr) throws TerminfindungBusinessException {
        awfTerminfindungAbschliessen.setzeVeranstaltungstermin(terminfindung, zeitraumNr);
    }

    @Override
    public void loescheZeitraeume(Terminfindung terminfindung, List<Zeitraum> zeitraumList) {
        awfTermineLoeschen.loescheZeitraeume(terminfindung, zeitraumList);
    }

    @Override
    public void aktualisiereTerminfindung(Terminfindung terminfindung, String organisatorName, String veranstaltungName) {
        awfAktualisiereTerminfindung.aktualisiereTerminfindung(terminfindung, organisatorName, veranstaltungName);
    }

}
