package de.msg.terminfindung.batch;

import java.util.Date;

import de.bund.bva.pliscommon.batchrahmen.batch.exception.BatchAusfuehrungsException;
import de.bund.bva.pliscommon.batchrahmen.batch.konfiguration.BatchKonfiguration;
import de.bund.bva.pliscommon.batchrahmen.batch.protokoll.BatchErgebnisProtokoll;
import de.bund.bva.pliscommon.batchrahmen.batch.rahmen.AuthenticationCredentials;
import de.bund.bva.pliscommon.batchrahmen.batch.rahmen.BatchAusfuehrungsBean;
import de.bund.bva.pliscommon.batchrahmen.batch.rahmen.BatchStartTyp;
import de.bund.bva.pliscommon.batchrahmen.batch.rahmen.VerarbeitungsErgebnis;
import org.springframework.transaction.annotation.Transactional;

public class TestTransactionalBatch2 implements BatchAusfuehrungsBean {

    @Override
    public int initialisieren(BatchKonfiguration konfiguration, long satzNummer, String dbKey,
        BatchStartTyp startTyp, Date datumLetzterErfolg, BatchErgebnisProtokoll protokoll)
        throws BatchAusfuehrungsException {
        return 0;
    }

    @Transactional
    @Override
    public VerarbeitungsErgebnis verarbeiteSatz() throws BatchAusfuehrungsException {
        return null;
    }

    @Override
    public void batchBeendet() {

    }

    @Override
    public void checkpointGeschrieben(long satzNummer) throws BatchAusfuehrungsException {

    }

    @Override
    public void vorCheckpointGeschrieben(long satzNummer) throws BatchAusfuehrungsException {

    }

    @Override
    public void rollbackDurchgefuehrt() {

    }

    @Override
    public void vorRollbackDurchgefuehrt() {

    }

    @Override
    public AuthenticationCredentials getAuthenticationCredentials(BatchKonfiguration konfiguration) {
        return null;
    }
}
