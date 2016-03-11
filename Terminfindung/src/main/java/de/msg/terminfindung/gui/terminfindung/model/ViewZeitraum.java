package de.msg.terminfindung.gui.terminfindung.model;

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


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse speichert einen Zeitraum in der View-Schicht.
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
public class ViewZeitraum implements Serializable{

	private static final long serialVersionUID = -457050384959229232L;

    /**
     * Nummer (Id) des Zeitraums.
     * Die Id des Zeitraums ist eindeutig über alle Tage der Terminfindung hinweg.
     * Sie identifiziert einen Zeitraum damit eindeutig ohne Angabe des Tages,
     * an dem der Zeitraum liegt.
     */
	private long zeitraum_nr;
    /**
     * Beschreibung des Zeitraums.
     */
	private String beschreibung;
    /**
     * Der Tag, an dem der Zeitraum liegt.
     */
	private ViewTag tag;
    /**
     * Eine Liste von Präferenzen für diesen Zeitraum aus Sicht des Zeitraums.
     * Wenn ein Teilnehmer eine Präferenz für diesen Zeitraum angibt,
     * wird in dieser Liste ein Objekt der Klasse {@link ViewTeilnehmerZeitraum}
     * hinzugefügt, das die Verbindung zwischen dem Teilnehmer und dem Zeitraum herstellt.
     * "Präferenz" für einen Zeitraum bedeutet nicht unbedingt, dass der Teilnehmer
     * sich FÜR diesen Zeitraum entscheidet, d.h. mit "Ja" abstimmt.
     * Jeder Teilnehmer trägt immer für jeden Zeitraum eine Präferenz ein wenn er abstimmt.
     * In diesem Sinne sind auch "Nein" und "Wenn es sein muss" Präferenzen.
     */
	private List<ViewTeilnehmerZeitraum> teilnehmerZeitraeume = new ArrayList<>();

    /**
     * Auswertung der Präferenzen durch Zählen der abgegebenen Stimmen für den Zeitraum.
     * Die Methode durchläuft dazu die Liste der Präferenzen und
     * zählt, wie viele davon "Ja", "Nein" oder "Wenn es sein muss" sind.
     *
     * @return Ein Integer Array mit drei Elementen, die die "Ja", "Nein" und "Wenn es sein muss"
     * Stimmen angeben.
     */
	public Integer[] zaehleStimmen () {

		Integer[] count={0,0,0};
		
		for (ViewTeilnehmerZeitraum tz : teilnehmerZeitraeume) {
			if (tz.getPraeferenz() == ViewPraeferenz.NEIN) count [0]++;
			else if (tz.getPraeferenz() == ViewPraeferenz.JA) count [1]++;
			else if (tz.getPraeferenz() == ViewPraeferenz.WENN_ES_SEIN_MUSS) count [2]++;
		}
		return count;
	}
	
	public List<ViewTeilnehmerZeitraum> getTeilnehmerZeitraeume() {
		return teilnehmerZeitraeume;
	}
	public void setTeilnehmerZeitraeume(
			List<ViewTeilnehmerZeitraum> teilnehmerZeitraeume) {
		this.teilnehmerZeitraeume = teilnehmerZeitraeume;
	}
	public ViewZeitraum(long zeitraum_nr, String beschreibung) {
		super();
		this.zeitraum_nr = zeitraum_nr;
		this.beschreibung = beschreibung;
	}
	public ViewZeitraum() {
		super();
	}
	public long getZeitraum_nr() {
		return zeitraum_nr;
	}
	public void setZeitraum_nr(long zeitraum_nr) {
		this.zeitraum_nr = zeitraum_nr;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public ViewTag getTag() {
		return tag;
	}
	public void setTag(ViewTag tag) {
		this.tag = tag;
	}
	
}
