package de.bund.bva.isyfact.terminfindung.batch;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bund.bva.pliscommon.batchrahmen.batch.rahmen.BatchStartTyp;
import de.bund.bva.pliscommon.batchrahmen.test.TestBatchLauchner;

import static org.junit.Assert.*;

/**
 * Testklasse für das Löschen von veralteten Terminfindungen via Batch.
 */
public class TerminfindungLoeschBatchTest {

    private static final String BATCH_KONFIGURATION_DATEI = "/resources/batch/batch-loeschen-config.properties";

    @Test
    public void testStarteBatchErfolgreich() {
        int result = starteBatch();
        assertEquals("Batch-Rückgabewert: ", 0, result);
    }

    private int starteBatch() {
        try {
            TestBatchLauchner launcher = new TestBatchLauchner(BATCH_KONFIGURATION_DATEI);
            return launcher.starteBatch(BatchStartTyp.START, new String[]{});
        } catch (IOException e) {
            e.printStackTrace();
            fail("Batchaufruf mit IOException fehlgeschlagen.");
            return 2;
        }
    }

    @Before
    public void erzeugeTestObjekte() {
    }

    @After
    public void entferneTestObjekte() {
    }

}
