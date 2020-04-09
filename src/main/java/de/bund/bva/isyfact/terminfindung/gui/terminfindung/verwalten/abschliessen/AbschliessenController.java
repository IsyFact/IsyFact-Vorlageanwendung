package de.bund.bva.isyfact.terminfindung.gui.terminfindung.verwalten.abschliessen;





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

import org.springframework.stereotype.Controller;

import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.isyfact.terminfindung.common.exception.TerminfindungBusinessException;
import de.bund.bva.isyfact.terminfindung.gui.awkwrapper.AwkWrapper;
import de.bund.bva.isyfact.terminfindung.gui.terminfindung.AbstractController;
import de.bund.bva.isyfact.terminfindung.gui.terminfindung.model.TerminfindungModel;

/**
 * Controller fuer den Abschliessen Flow
 *
 * @author msg systems ag, Maximilian Falter
 */
@Controller
public class AbschliessenController extends AbstractController<AbschliessenModel> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(AbschliessenController.class);

    /**
     * Ruft den AWK auf, um einen definitiven Zeitraum (Tag) fuer die Terminfindung festzulegen, zusaetzlich wird noch
     * im Model die Variable istAbgeschlossen auf true gesetzt, damit keine Aktionen, welche die Terminfindung
     * manipulieren koennten, ausfuehrbar sind.
     *
     * @param model Das Modell
     */
    public void schliesseTerminfindung(AbschliessenModel model) {

        LOG.debug("Schließe die Terminfindung ab.");

        AwkWrapper awk = super.getAwk();

        long zeitraumNr = model.getSelectedZeitraumNr();

        try {
            TerminfindungModel terminfindung = awk.setzeVeranstaltungstermin(model.getTerminfindung(), zeitraumNr);
            model.setTerminfindung(terminfindung);
        } catch (TerminfindungBusinessException e) {
            LOG.error("Fehler beim Abschluss der Terminfindung: " ,e);
        }
    }
}
