package de.bund.bva.isyfact.terminfindung.gui.terminfindung.erstellen;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import de.bund.bva.isyfact.datetime.core.Zeitraum;
import de.bund.bva.isyfact.datetime.format.InFormat;
import de.bund.bva.isyfact.datetime.format.OutFormat;
import de.bund.bva.isyfact.datetime.util.DateTimeUtil;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.isyfact.terminfindung.common.exception.TerminfindungBusinessException;
import de.bund.bva.isyfact.terminfindung.gui.terminfindung.AbstractController;
import de.bund.bva.isyfact.terminfindung.gui.terminfindung.model.TagModel;
import de.bund.bva.isyfact.terminfindung.gui.terminfindung.model.TerminfindungModel;
import de.bund.bva.isyfact.terminfindung.gui.terminfindung.model.ZeitraumModel;
import de.bund.bva.isyfact.terminfindung.gui.util.DataGenerator;

/*
 * #%L
 * Terminfindung (Appointments)
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
 * Controller for the flow "Erstellen": Creation of a new appointment
 *
 * @author msg systems ag
 */
@Controller
public class ErstellenController extends AbstractController<ErstellenModel> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(ErstellenController.class);

    public void initialisiereModel(ErstellenModel model) {
        super.initialisiereModell(model);

        model.setPlaceholderDate(OutFormat.DATUM.format(DateTimeUtil.localDateNow().plusDays(1)));

        if (model.isTestMode()) {
            LOG.debug("TestMode: Erzeuge Tage");
            model.setTage(DataGenerator.generateTage(getKonfiguration()));
            model.setName("Test-Veranstaltung");
            model.setOrgName("Test-Organisation");
        }
    }

    /**
     * Adds a day to the list of days.
     *
     * @param model the model
     */
    public void fuegeDatumHinzu(ErstellenModel model) {

        List<ValidationMessage> validationMessages = new ArrayList<>();

        if (istValideEingabe(model, validationMessages)) {
            fuegeDatumZuModelHinzu(model.getNewDate(), model);
        } else {
            globalFlowController.getValidationController().processValidationMessages(validationMessages);
        }
    }

    private void fuegeDatumZuModelHinzu(LocalDate datum, ErstellenModel model) {
        LOG.debug("Füge Tag hinzu.");
        TagModel tag = new TagModel();
        tag.setDatum(datum);
        tag.setZeitraumVon(leseZeitAusKonfiguration("termin.start.vorgabe"));
        tag.setZeitraumBis(leseZeitAusKonfiguration("termin.ende.vorgabe"));
        model.getTage().add(tag);
        Collections.sort(model.getTage());
    }

    private LocalTime leseZeitAusKonfiguration(String schluessel) {
        return LocalTime.parse(getKonfiguration().getAsString(schluessel), InFormat.ZEIT_0H);
    }

    private boolean istValideEingabe(ErstellenModel model, List<ValidationMessage> validationMessages) {
        if (maxAnzahlTageUeberschritten(model)) {
            validationMessages.add(
                    new ValidationMessage("DA", "newDate", "Datum", "Bereits max. Anzahl an Daten hinzugefügt"));
            return false;
        } else if (keineEingabe(model)) {
            validationMessages.add(new ValidationMessage("DA", "newDate", "Datum", "Benötigtes Feld"));
            return false;
        }

        LocalDate addedDate = model.getNewDate();
        if (datumLiegtInVergangenheit(addedDate)) {
            validationMessages.add(new ValidationMessage("DA", "newDate", "Datum",
                    "Das Datum darf nicht in der Vergangenheit liegen"));
            return false;
        } else if (datumBereitsVorhanden(model, addedDate)) {
            validationMessages
                    .add(new ValidationMessage("DA", "newDate", "Datum", "Datum bereits hinzugefügt"));
            return false;
        }

        return true;
    }

    private boolean maxAnzahlTageUeberschritten(ErstellenModel model) {
        return model.getTage().size() >= getKonfiguration().getAsInteger("termin.tag.max.number");
    }


    private boolean keineEingabe(ErstellenModel model) {
        return model.getNewDate() == null;
    }

    private boolean datumLiegtInVergangenheit(LocalDate date) {
        return date.isBefore(DateTimeUtil.localDateNow());
    }

    private boolean datumBereitsVorhanden(ErstellenModel model, LocalDate date) {
        return model.getTage().stream().anyMatch(tagModel -> tagModel.getDatum().isEqual(date));
    }

    /**
     * Deletes a day from the local list of days (data)
     *
     * @param model the model
     */
    public void loescheDatum(ErstellenModel model) {
        model.getTage().remove(model.getSelectedTermin());
    }

    /**
     * Adds a time period to the local list of time periods (data).
     *
     * @param model the model
     */
    public void fuegeZeitraumHinzu(ErstellenModel model) {

        List<ValidationMessage> validationMessages = new ArrayList<>();

        boolean zeitraumExists = model.getSelectedTermin().getZeitraeume().stream().anyMatch(
                z -> z.getZeitraum().equals(Zeitraum
                        .of(model.getSelectedTermin().getZeitraumVon(), model.getSelectedTermin().getZeitraumBis())));

        // maximum number of days already available?
        if (model.getSelectedTermin().getZeitraeume().size() >= getKonfiguration()
                .getAsInteger("termin.tag.zeitraum.max.number")) {
            validationMessages.add(
                    new ValidationMessage("DA", "zeitraeume_" + model.getSelectedTermin().getShortDate(),
                            "Zeitraum", "Bereits max. Anzahl an Daten hinzugefügt"));
        } else if (
                model.getSelectedTermin().getZeitraumVon().compareTo(model.getSelectedTermin().getZeitraumBis())
                        == 0) {
            validationMessages.add(
                    new ValidationMessage("DA", "zeitraeume_" + model.getSelectedTermin().getShortDate(),
                            "Zeitraum", "Zeitraum beginnt und Enden um die gleiche Uhrzeit."));
        } else if (
                model.getSelectedTermin().getZeitraumVon().compareTo(model.getSelectedTermin().getZeitraumBis())
                        > 0) {
            validationMessages.add(
                    new ValidationMessage("DA", "zeitraeume_" + model.getSelectedTermin().getShortDate(),
                            "Zeitraum", "Zeitraum startet nach seinem Ende."));
        } else if (zeitraumExists) {
            validationMessages.add(
                    new ValidationMessage("DA", "zeitraeume_" + model.getSelectedTermin().getShortDate(),
                            "Zeitraum", "Zeitraum existiert bereits."));
        } else {
            ZeitraumModel zeitraum = new ZeitraumModel();
            zeitraum.setZeitraum(Zeitraum
                    .of(model.getSelectedTermin().getZeitraumVon(), model.getSelectedTermin().getZeitraumBis()));
            model.getSelectedTermin().getZeitraeume().add(zeitraum);
            model.getSelectedTermin().setZeitraumVon(leseZeitAusKonfiguration("termin.start.vorgabe"));
            model.getSelectedTermin().setZeitraumBis(leseZeitAusKonfiguration("termin.ende.vorgabe"));
            Collections.sort(model.getSelectedTermin().getZeitraeume());
            return;
        }

        this.globalFlowController.getValidationController().processValidationMessages(validationMessages);
    }

    /**
     * Deletes a time period from the local list of time periods (data))
     *
     * @param model the model
     */
    public void loescheZeitraum(ErstellenModel model) {
        for (TagModel tag : model.getTage()) {
            tag.getZeitraeume().remove(model.getSelectedZeitraum());
        }
    }

    /**
     * Validates the model before saving. It is checked whether the mandatory fields are filled.
     *
     * @param model the model
     * @return true if all mandatory fields are filled, otherwise false
     */
    public boolean validiereStammdaten(ErstellenModel model) {
        List<ValidationMessage> validationMessages = new ArrayList<>();
        if (StringUtils.isBlank(model.getName())) {
            validationMessages.add(new ValidationMessage("TI", "name", "Titel", "Benötigtes Feld"));
        }
        if (StringUtils.isBlank(model.getOrgName())) {
            validationMessages.add(new ValidationMessage("NA", "orgName", "Ihr Name", "Benötigtes Feld"));
        }

        if (validationMessages.isEmpty()) {
            return true;
        } else {
            globalFlowController.getValidationController().processValidationMessages(validationMessages);
            return false;
        }
    }

    /**
     * Validates the model before saving.
     * It is checked whether all days contain at least one period. If the model is consistent, it is saved.
     */
    public boolean validiereTermine(ErstellenModel model) {
        List<ValidationMessage> validationMessages = new ArrayList<>();

        for (TagModel tag : model.getTage()) {
            if (tag.getZeitraeume().isEmpty()) {
                validationMessages.add(new ValidationMessage("DA", "zeitraeume", "Datum",
                        "Dem Datum " + tag.getShortDate() + " ist kein Zeitraum zugeordnet."));
            }
        }

        if (validationMessages.isEmpty()) {
            return speichereModel(model);
        } else {
            globalFlowController.getValidationController().processValidationMessages(validationMessages);
            return false;
        }
    }

    /**
     * Saves the newly created appointment determination.
     * The method calls the wrapper of the application core with the data available in the model.
     *
     * @param model The model whose content is stored
     */
    private boolean speichereModel(ErstellenModel model) {

        LOG.debug("Speichere Terminfindung.");

        try {
            TerminfindungModel
                    terminfindung = getAwk().erstelleTerminfindung(model.getOrgName(), model.getName(), model.getTage());
            model.setTerminfindung(terminfindung);
            return true;
        } catch (TerminfindungBusinessException e) {
            LOG.errorFachdaten(e.getAusnahmeId(), "Fehler beim Erstellen der Terminfindung", e);
            return false;
        }
    }

}