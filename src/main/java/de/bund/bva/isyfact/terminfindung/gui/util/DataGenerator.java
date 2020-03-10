package de.bund.bva.isyfact.terminfindung.gui.util;

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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.bund.bva.isyfact.datetime.core.Zeitraum;
import de.bund.bva.isyfact.datetime.format.InFormat;
import de.bund.bva.isyfact.datetime.util.DateTimeUtil;
import de.bund.bva.isyfact.terminfindung.gui.terminfindung.model.TagModel;
import de.bund.bva.isyfact.terminfindung.gui.terminfindung.model.ZeitraumModel;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;

/**
 * Generierung von Testdaten für manuelle Tests.
 *
 * @author msg systems ag, Dirk Jäger
 */
public class DataGenerator {

    /**
     * Erzeugte eine Dummy-Liste von Tagen mit Zeiträumen.
     * Zum Befüllen des Modells für Testzwecke gedacht.
     * @param konfiguration
     *
     * @return Eine fest vorgegebene Test-Liste mit Terminen
     */
    public static List<TagModel> generateTage(Konfiguration konfiguration)  {
        LocalTime startZeit = LocalTime.parse(konfiguration.getAsString("termin.start.vorgabe"), InFormat.ZEIT_0H);
        LocalTime endeZeit = LocalTime.parse(konfiguration.getAsString("termin.ende.vorgabe"), InFormat.ZEIT_0H);

        List<TagModel> tage = new ArrayList<>();
        // Erzeuge eine Liste von drei Tagen
        for (int i=0; i<=2; i++) {
            TagModel tag = new TagModel();

            tag.setDatum(DateTimeUtil.localDateNow().plusDays(i));
            tag.setZeitraumVon(startZeit);
            tag.setZeitraumBis(endeZeit);

            List<ZeitraumModel> zeitraeume = new ArrayList<>();
            for (int j=0; j<=2; j++) {

                ZeitraumModel zeitraum = new ZeitraumModel();
                zeitraum.setZeitraum(Zeitraum.of(LocalTime.of(j+8, 0), LocalTime.of(j+9, 0)));
                zeitraeume.add(zeitraum);
            }
            tag.setZeitraeume(zeitraeume);

            tage.add(tag);
        }

        return tage;
    }

    /**
     * Erzeugt die Uhrzeiten 00:00 - 23:45 im Abstand von 15 Minuten.
     *
     * @return die Uhrzeiten 00:00 - 23:45 im Abstand von 15 Minuten.
     */
    public static List<LocalTime> getUhrzeitAuswahl(){
        return IntStream.range(0, 96)
                        .mapToObj(i -> LocalTime.of(0, 0).plusMinutes(15L * i))
                        .collect(Collectors.toList());
    }
}
