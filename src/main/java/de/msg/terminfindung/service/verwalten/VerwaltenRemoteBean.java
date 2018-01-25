package de.msg.terminfindung.service.verwalten;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;

public interface VerwaltenRemoteBean {

    void verwalten(String param1, int param2) throws TerminfindungBusinessException;
}
