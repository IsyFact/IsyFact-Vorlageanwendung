package de.bund.bva.isyfact.terminfindung.core.admin;

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

import javax.persistence.EntityManager;

import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.ueberwachung.admin.impl.WatchdogImpl;
import org.springframework.beans.factory.InitializingBean;

public class TerminfindungWatchdog extends WatchdogImpl implements InitializingBean {

    /**
     * SQL-Query, welche der Watchdog zur Prüfung der Datenbankverbindung ausführt.
     */
    private static final String CONF_ADMIN_WATCHDOG_VALIDATION_QUERY = "admin.watchdog.validation.query";

    private Konfiguration konfiguration;

    private EntityManager entityManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        addPruefung("Datenbank", () -> {
            final String watchdogQuery = konfiguration.getAsString(CONF_ADMIN_WATCHDOG_VALIDATION_QUERY);
            entityManager.createNativeQuery(watchdogQuery).getSingleResult();
            return true;
        });
    }

    public void setKonfiguration(Konfiguration konfiguration) {
        this.konfiguration = konfiguration;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
