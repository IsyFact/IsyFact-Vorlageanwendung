package de.msg.terminfindung.jqassistant;

import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.konfiguration.common.impl.AbstractKonfiguration;
import de.bund.bva.pliscommon.konfiguration.common.impl.PropertyKonfiguration;
import de.msg.terminfindung.common.konstanten.KonfigurationSchluessel;

/**
 * Test für die Constraints von JQAssistant.
 */
public class JQAssistantViolationTest {

    private Konfiguration konfiguration;

    // Verletzung von konfiguration:BenutzeKeineInternenKlassen
    private PropertyKonfiguration propertyKonfiguration;

    private String quatsch;

    public JQAssistantViolationTest(PropertyKonfiguration konfiguration, Konfiguration konf2) {
        propertyKonfiguration = konfiguration;
        this.konfiguration = konf2;

        // Verletzung von konfiguration:SpeichereKeineKonfigurationsParameter
        quatsch = this.konfiguration.getAsString(KonfigurationSchluessel.BATCH_ORGANISATION);
        quatsch = konf2.getAsString(KonfigurationSchluessel.BATCH_ORGANISATION);

        ((AbstractKonfiguration) propertyKonfiguration).getAsBoolean(KonfigurationSchluessel.BATCH_ORGANISATION);
    }

}
