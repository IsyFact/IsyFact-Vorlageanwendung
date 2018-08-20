package de.msg.terminfindung.gui.awkwrapper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.common.konstanten.FehlerSchluessel;
import de.msg.terminfindung.core.erstellung.Erstellung;
import de.msg.terminfindung.core.teilnahme.Teilnahme;
import de.msg.terminfindung.core.verwaltung.Verwaltung;
import de.msg.terminfindung.gui.awkwrapper.AwkWrapper;
import de.msg.terminfindung.gui.terminfindung.model.PraeferenzModel;
import de.msg.terminfindung.gui.terminfindung.model.TagModel;
import de.msg.terminfindung.gui.terminfindung.model.TeilnehmerModel;
import de.msg.terminfindung.gui.terminfindung.model.TerminfindungModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;
import de.msg.terminfindung.persistence.entity.Praeferenz;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Teilnehmer;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
 * Implementierung des Anwendungskern-Wrappers.
 *
 * @author Dirk Jäger
 */
@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
public class AwkWrapperImpl implements AwkWrapper {

	/**
	 * Komponente des Anwendungskerns für die Erstellung von Terminfindungen
	 */
	private final Erstellung erstellung;

	/**
	 * Komponente des Anwendungskerns für die Verwaltung von Terminfindungen
	 */
	private final Verwaltung verwaltung;

	/**
	 * Komponente des Anwendungskerns für die Teilnahme an Terminfindungen
	 */
	private final Teilnahme teilnahme;

	/**
	 * Bean-Mapper für die Abbildung zwischen View-Objekten und
	 * Persistenz-Objekten
	 */
	private final Mapper beanMapper;

	public AwkWrapperImpl(Erstellung erstellung, Verwaltung verwaltung, Teilnahme teilnahme, Mapper beanMapper) {
		this.erstellung = erstellung;
		this.verwaltung = verwaltung;
		this.teilnahme = teilnahme;
		this.beanMapper = beanMapper;
	}

	@Override
	public TerminfindungModel erstelleTerminfindung(String organisatorName, String veranstaltungName,
			List<TagModel> tage) throws TerminfindungBusinessException {

		List<Tag> termine = new ArrayList<>();
		for (TagModel tag : tage) {
			termine.add(beanMapper.map(tag, Tag.class));
		}

		Terminfindung terminfindung = erstellung.erstelleTerminfindung(organisatorName, veranstaltungName, termine);

		return map(terminfindung);
	}

	@Override
	public TerminfindungModel ladeTerminfindung(UUID terminfindungsRefNr) throws TerminfindungBusinessException {

		Terminfindung tf = verwaltung.leseTerminfindung(terminfindungsRefNr);
		return map(tf);
	}

	@Override
	public TerminfindungModel setzeVeranstaltungstermin(TerminfindungModel terminfindungModel, long zeitraumNr)
			throws TerminfindungBusinessException {

		if (terminfindungModel == null)
			throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_UNGUELTIG);

		Terminfindung terminfindung = verwaltung.leseTerminfindung(terminfindungModel.getIdRef());

		verwaltung.setzeVeranstaltungstermin(terminfindung, zeitraumNr);

		// gib die aktualisierte Terminfindung als Ergebnis zurück
		return map(terminfindung);
	}

	@Override
	public TerminfindungModel bestaetigeTeilnahme(TerminfindungModel terminfindungModel,
			TeilnehmerModel teilnehmerModel, Map<ZeitraumModel, PraeferenzModel> viewTerminwahl)
			throws TerminfindungBusinessException {

		if (terminfindungModel == null || teilnehmerModel == null || viewTerminwahl == null)
			throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_UNGUELTIG);

		// Übertrage die Datenstrukturen aus dem View in die Struktur des
		// Anwendungskerns
		// Lese die Terminfindung anhand ihrer Id
		Terminfindung terminfindung = verwaltung.leseTerminfindung(terminfindungModel.getIdRef());

		// Der Teilnehmer wird neu erzeugt, der Name wird übertragen
		Teilnehmer teilnehmer = new Teilnehmer();
		teilnehmer.setName(teilnehmerModel.getName());

		// Suche in den gegebenen Zeiträumen der Terminwahl nach den IDs der
		// Zeiträume, die in der Map übergeben wurden
		// Konstruiere daraus die entsprechende Map für den Aufruf des
		// Anwendungskerns
		Map<Zeitraum, Praeferenz> terminwahl = new HashMap<>();
		for (ZeitraumModel zeitraumModel : viewTerminwahl.keySet()) {

			Zeitraum zeitraum = terminfindung.findeZeitraumById(zeitraumModel.getId());

			// Wenn in der Terminfindung kein Zeitraum mit der gesuchten Id
			// exisistiert ist die Anfrage ungültig
			if (zeitraum == null)
				throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_UNGUELTIG);

			// Bilde den View-Präferenzwert auf den entsprechenden
			// Persistenz-Präferenzwert ab und speichere
			Praeferenz praeferenz = map(viewTerminwahl.get(zeitraumModel));
			terminwahl.put(zeitraum, praeferenz);
		}
		// rufe den Anwendungskern auf
		teilnahme.bestaetigeTeilnahme(terminfindung, teilnehmer, terminwahl);

		// gib die aktualisierte Terminfindung als Ergebnis zurück
		return map(terminfindung);
	}

	@Override
	public TerminfindungModel loescheZeitraeume(TerminfindungModel terminfindungModel,
			List<ZeitraumModel> viewZeitraeume) throws TerminfindungBusinessException {

		if (terminfindungModel == null || viewZeitraeume == null)
			throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_UNGUELTIG);

		// Übertrage die Datenstrukturen aus dem View in die Struktur des
		// Anwendungskerns
		// Lese die Terminfindung anhand ihrer Id, Konstruiere die entsprechende
		// Liste für den Aufruf des
		// Anwendungskerns
		List<Zeitraum> zeitraumList = new ArrayList<>();
		Terminfindung terminfindung = verwaltung.leseTerminfindung(terminfindungModel.getIdRef());

		// Hole zu jedem zu löschenden Zeitraum das entsprechende Objekt des
		// Anwendungskerns
		for (ZeitraumModel zeitraumModel : viewZeitraeume) {

			Zeitraum zeitraum = terminfindung.findeZeitraumById(zeitraumModel.getId());
			// Wenn in der Terminfindung kein Zeitraum mit der gesuchten Id
			// exisistiert ist die Anfrage ungültig
			if (zeitraum == null)
				throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_UNGUELTIG);

			zeitraumList.add(zeitraum);
		}

		// rufe den Anwendungskern auf
		verwaltung.loescheZeitraeume(terminfindung, zeitraumList);

		// gib die aktualisierte Terminfindung als Ergebnis zurück
		return map(terminfindung);
	}

	/**
	 * Kapselt das Mapping zwischen dem Persistenz-Objekt und dem View-Objekt
	 * einer Terminfindung. Die Methode verwendet intern Dozer für das
	 * eigentlichen Mapping.
	 *
	 * @param terminfindung
	 *            Das Persistenz-Objekt der Terminfindung
	 * @return Das View-Objekt der Terminfindung
	 */
	private TerminfindungModel map(Terminfindung terminfindung) {
		return beanMapper.map(terminfindung, TerminfindungModel.class);
	}

	/**
	 * Bildet einen View-Präferenzwert auf einen Persistenz-Präferenzwert ab.
	 * Die Abbildung ist trivial, sie dient aber dazu die Modelle wirksam zu
	 * entkoppeln. Das die numerische Codierung des Enums sowohl in der
	 * Datenbank abgelegt wird, als auch im GUI sichbar ist, scheint es
	 * sinnvoll, hier ein Mapping einzuführen, das sich nicht auf diese
	 * Codierung verlässt, sondern einzelne Werte explizit einander zuordnet.
	 * Auf eine Implementierung über Dozer wurde verzichtet, da hierfür
	 * ebenfalls eigener Mapping Code erforderlich wäre (Einzelne Enum-Werte
	 * können von Dozer standardmäßig nicht abgebildet werden.)
	 *
	 * @param praeferenzModel
	 *            der View-Präferenzwert
	 * @return der Persistenz-Präferenzwert
	 * @throws TerminfindungBusinessException
	 *             Wird bei unbekannten View-Präferenzewerten erzeugt
	 */
	private Praeferenz map(PraeferenzModel praeferenzModel) throws TerminfindungBusinessException {

		switch (praeferenzModel) {
		case JA:
			return Praeferenz.JA;
		case NEIN:
			return Praeferenz.NEIN;
		case WENN_ES_SEIN_MUSS:
			return Praeferenz.WENN_ES_SEIN_MUSS;
		default:
			throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_UNGUELTIG);
		}
	}

	@Override
	public TerminfindungModel aktualisiereTerminfindung(TerminfindungModel terminfindungModel,
			String veranstaltungsName, String organisatorName) throws TerminfindungBusinessException {

		if (terminfindungModel == null || veranstaltungsName == null || organisatorName == null)
			throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_UNGUELTIG);

		Terminfindung terminfindung = verwaltung.leseTerminfindung(terminfindungModel.getIdRef());
		verwaltung.aktualisiereTerminfindung(terminfindung, organisatorName, veranstaltungsName);
		return map(terminfindung);
	}

	@Override
	public List<TerminfindungModel> ladeAlleTerminfindungen() {

		List<Terminfindung> alleTerminfindungen = verwaltung.leseAlleTerminfindungen();
		List<TerminfindungModel> model = new ArrayList<>();

		for (Terminfindung tf : alleTerminfindungen) {
			model.add(map(tf));
		}

		return model;
	}
}
