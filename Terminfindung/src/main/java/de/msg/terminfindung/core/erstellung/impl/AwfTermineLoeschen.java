package de.msg.terminfindung.core.erstellung.impl;

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


import de.msg.terminfindung.persistence.dao.TerminDao;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.dao.ZeitraumDao;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;

import java.util.Iterator;
import java.util.List;

/**
 * @author msg systems ag, Dirk Jäger
 *
 * Diese Klasse implementiert den Anwendungsfall "Termine loeschen"
 */
public class AwfTermineLoeschen {
	
	private ZeitraumDao zeitraumDao;
	private TerminDao tagDao;
    private TerminfindungDao terminfindungDao;

    /**
     * Lösche einen einzelnen Zeitraum aus einer Terminfindung.
     * Die Methode prüft nicht, ob der Tag, zu dem der Zeitraum gehört,
     * danach noch weitere Zeiträume enthält
     *
     * @param terminfindung Die Terminfindung
     * @param zeitraum Der zu löschende Zeitraum
     */
    private void loescheZeitraum(Terminfindung terminfindung, Zeitraum zeitraum){

		// Hole zuerst den Tag, zu dem der Zeitraum gehört
		Tag tag= zeitraum.getTag();

		// Lösche dann den Zeitraum
        tag.getZeitraeume().remove(zeitraum);
		zeitraumDao.loesche(zeitraum);
	}

    /**
     * Löscht eine Liste von Zeiträumen aus einer Terminfindung.
     * Die Methode prüft ob durch das Löschen leere Tage (ohne
     * zugeordnete Zeiträume) entstehen und löscht diese dann
     * ebenfalls
     *
     * @param terminfindung Die Terminfindung
     * @param zeitraumList Die Liste der Zeiträume
     */
	public void loescheZeitraeume(Terminfindung terminfindung,
                                  List<Zeitraum> zeitraumList) {

        // Lösche die Zeiträume aus der Liste
		if (zeitraumList != null) {
			for (Zeitraum z : zeitraumList) {
				loescheZeitraum(terminfindung, z);
			}
		}

        // Iteriere über alle Tage, falls nach dem Löschen ein Tag keine
        // Zeiträume mehr hat, löschen den Tag.
        List<Tag> tagList = terminfindung.getTermine();
        if (tagList != null) {
            Iterator<Tag> tagIterator = terminfindung.getTermine().iterator();
            while (tagIterator.hasNext()) {
                Tag tag = tagIterator.next();
                if (tag.getZeitraeume().isEmpty()) {
                    tagDao.loesche(tag);
                    tagIterator.remove();
                }
            }
        }

        terminfindungDao.updateTerminfindung(terminfindung);
	}

	/*--------------------------------------------------------------------------
	 *  Getter und Setter
	 *--------------------------------------------------------------------------*/

    public ZeitraumDao getZeitraumDao() {
        return zeitraumDao;
    }

    public void setZeitraumDao(ZeitraumDao zeitraumDao) {
        this.zeitraumDao = zeitraumDao;
    }

    public TerminDao getTagDao() {
        return tagDao;
    }

    public void setTagDao(TerminDao tagDao) {
        this.tagDao = tagDao;
    }

    public TerminfindungDao getTerminfindungDao() {
        return terminfindungDao;
    }

    public void setTerminfindungDao(TerminfindungDao terminfindungDao) {
        this.terminfindungDao = terminfindungDao;
    }

}
