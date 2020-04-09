package de.bund.bva.isyfact.terminfindung.gui.login;

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

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Controller;

import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.isyfact.logging.LogKategorie;
import de.bund.bva.isyfact.terminfindung.common.konstanten.EreignisSchluessel;
import de.bund.bva.isyfact.terminfindung.gui.terminfindung.AbstractController;
import de.bund.bva.isyfact.terminfindung.sicherheit.SerializableAufrufKontextImpl;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import de.bund.bva.pliscommon.aufrufkontext.impl.AufrufKontextImpl;
import de.bund.bva.pliscommon.sicherheit.Berechtigungsmanager;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.sicherheit.common.exception.AuthentifizierungTechnicalException;

/**
 * Controller des Login Flows.
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
@Controller
public class LoginController extends AbstractController<LoginModel> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(LoginController.class);

    private Sicherheit<AufrufKontextImpl> sicherheit;

    private AufrufKontextVerwalter<SerializableAufrufKontextImpl> aufrufKontextVerwalter;

    /**
     * Initialisiert das Modell des Loeschen Flows.
     *
     * @param model Das Modell
     */
    public void initialisiereModel(LoginModel model) {
    }

    /**
     * Führt den Login-Vorgang aus.
     *
     * @param model   Das Modell
     * @param context Der Login-Kontext
     */
    public boolean performLogin(LoginModel model, MessageContext context) {

        LOG.infoFachdaten(LogKategorie.JOURNAL, EreignisSchluessel.MSG_LOGIN_STARTED,
            "Führe Login aus für Benutzer {}", model.getUsername());

        SerializableAufrufKontextImpl aufrufKontext = new SerializableAufrufKontextImpl();

        aufrufKontext.setDurchfuehrenderBenutzerKennung(model.getUsername());
        aufrufKontext.setDurchfuehrenderBenutzerPasswort(model.getPassword());

        aufrufKontextVerwalter.setAufrufKontext(aufrufKontext);

        try {
            @SuppressWarnings("unused")
            Berechtigungsmanager berechtigungsmanager =
                sicherheit.getBerechtigungsManagerUndAuthentifiziere(aufrufKontext);

            LOG.info(LogKategorie.JOURNAL, EreignisSchluessel.MSG_LOGIN_SUCCESS,
                "Authentifizierung war erfolgreich");
        } catch (AuthentifizierungTechnicalException e) {

            LOG.info(LogKategorie.JOURNAL, EreignisSchluessel.MSG_LOGIN_FAILED,
                "Authentifizierung ist fehlgeschlagen", e);

            context.addMessage(
                new MessageBuilder().error().defaultText("Authentifizierung ist fehlgeschlagen").build());
            return false;
        }
        return true;
    }

    public Sicherheit<AufrufKontextImpl> getSicherheit() {
        return sicherheit;
    }

    public void setSicherheit(Sicherheit<AufrufKontextImpl> sicherheit) {
        this.sicherheit = sicherheit;
    }

    public AufrufKontextVerwalter<SerializableAufrufKontextImpl> getAufrufKontextVerwalter() {
        return aufrufKontextVerwalter;
    }

    public void setAufrufKontextVerwalter(
        AufrufKontextVerwalter<SerializableAufrufKontextImpl> aufrufKontextVerwalter) {
        this.aufrufKontextVerwalter = aufrufKontextVerwalter;
    }
}
