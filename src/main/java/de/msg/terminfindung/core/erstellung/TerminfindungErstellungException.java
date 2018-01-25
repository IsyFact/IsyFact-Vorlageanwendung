package de.msg.terminfindung.core.erstellung;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;

public class TerminfindungErstellungException extends TerminfindungBusinessException {

    public TerminfindungErstellungException(String ausnahmeId, String... parameter) {
        super(ausnahmeId, parameter);
    }

    public TerminfindungErstellungException(String ausnahmeId, Throwable throwable, String... parameter) {
        super(ausnahmeId, throwable, parameter);
    }
}
