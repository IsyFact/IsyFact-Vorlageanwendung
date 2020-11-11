package de.bund.bva.isyfact.terminfindung.core.datenpflege.impl;

import java.time.LocalDate;

import de.bund.bva.isyfact.terminfindung.common.exception.TerminfindungBusinessException;
import de.bund.bva.isyfact.terminfindung.core.datenpflege.Datenpflege;
import de.bund.bva.isyfact.terminfindung.persistence.dao.TerminfindungDao;

/**
 * Implementierung der Anwendungskomponente "Datenpflege" zur Pflege des Datenbestands.
 *
 * @author msg systems ag
 */
public class DatenpflegeImpl implements Datenpflege {

    private final AwfVergangeneTermineLoeschen awfVergangeneTermineLoeschen;

    public DatenpflegeImpl(TerminfindungDao terminfindungDao) {
        awfVergangeneTermineLoeschen = new AwfVergangeneTermineLoeschen(terminfindungDao);
    }

    @Override
    public int loescheVergangeneTerminfindungen(LocalDate stichtag) throws TerminfindungBusinessException {
        return awfVergangeneTermineLoeschen.loescheVergangeneTerminfindungen(stichtag);
    }

}
