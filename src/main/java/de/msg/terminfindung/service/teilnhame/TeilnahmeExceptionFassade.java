package de.msg.terminfindung.service.teilnhame;

import de.bund.bva.pliscommon.serviceapi.core.aop.StelltLoggingKontextBereit;
import de.bund.bva.pliscommon.serviceapi.service.httpinvoker.v1_0_0.AufrufKontextTo;
import de.bund.bva.pliscommon.serviceapi.service.httpinvoker.v1_0_0.ClientAufrufKontextTo;

public class TeilnahmeExceptionFassade implements TeilnahmeRemoteBean {

    @Override
    public void teilnehmen(AufrufKontextTo aufrufKontextTo, String param1, int param2)
        throws TeilnahmeVerarbeitungToException {

    }

    @Override
    @StelltLoggingKontextBereit
    public void nochmalTeilnehmen(ClientAufrufKontextTo clientAufrufKontextTo, String param1, int param2)
        throws TeilnahmeVerarbeitungToException {

    }
}
