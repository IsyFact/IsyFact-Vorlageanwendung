package de.msg.terminfindung.service.teilnhame;

import de.bund.bva.pliscommon.exception.service.PlisTechnicalToException;

public class TeilnahmeVerarbeitungToException extends PlisTechnicalToException {
    /**
     * Einziger Konstruktor. Es ist notwendig die Nachricht direkt zu übergeben, da diese nicht
     * nachträglich gesetzt werden kann. Zusätzlich nimmt dieser Konstrukt noch die Ausnahme-ID und
     * die Unique-ID entgegen.
     *
     * @param message    Fehlertext.
     * @param ausnahmeId AusnahmeID
     * @param uniqueId
     */
    protected TeilnahmeVerarbeitungToException(String message, String ausnahmeId, String uniqueId) {
        super(message, ausnahmeId, uniqueId);
    }
}
