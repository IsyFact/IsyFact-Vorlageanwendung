package de.msg.terminfindung.common.konstanten;

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

/**
 * iese Klasse enthält die Konstanten Ereitgnissschluessel
 *
 * @author vadim
 */
public abstract class EreignisSchluessel {

    /**
     * Login im Gange
     */
    public static final String MSG_LOGIN_STARTED = "USR00010";
    /**
     * Benutzer eingeloggt
     */
    public static final String MSG_LOGIN_SUCCESS = "USR00011";
    /**
     * Authenifizierung fehlgeschlagen
     */
    public static final String MSG_LOGIN_FAILED = "USR00012";

    /**
     * Hole Terminfindung
     */
    public static final String MSG_TERMINFINDUNG_GET = "USR00020";

    public static final String MSG_BATCH_START_NEU = "BAT00001";
    public static final String MSG_BATCH_START_WIEDERHOLUNG = "BAT00002";
    public static final String MSG_BATCH_CHECKPOINT = "BAT00003";
    public static final String MSG_BATCH_ROLLBACK = "BAT00004";
    public static final String MSG_BATCH_BEENDET = "BAT00005";
}
