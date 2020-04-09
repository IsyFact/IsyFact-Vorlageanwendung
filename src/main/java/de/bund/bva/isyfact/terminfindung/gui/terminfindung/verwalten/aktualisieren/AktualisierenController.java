package de.bund.bva.isyfact.terminfindung.gui.terminfindung.verwalten.aktualisieren;

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

import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.isyfact.terminfindung.common.exception.TerminfindungBusinessException;
import de.bund.bva.isyfact.terminfindung.gui.terminfindung.AbstractController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller für den Aktualisieren flow
 * @author vadim
 *
 */
public class AktualisierenController extends AbstractController<AktualisierenModel> {

	private static final IsyLogger LOG = IsyLoggerFactory.getLogger(AktualisierenController.class);

	public boolean aktualisiereTerminfindung(AktualisierenModel model){
		
		List<ValidationMessage> validationMessages = new ArrayList<>();
		
		if (model.getTerminfindung().getVeranstaltungName().isEmpty()) {
            validationMessages.add(new ValidationMessage("DA",
                    "formular1", "Name der Veranstaltung",
                    "Name der Veranstaltung kann nicht leer sein."));
        }
		if (model.getTerminfindung().getOrganisator().getName().isEmpty()) {
            validationMessages.add(new ValidationMessage("DA",
                    "formular3", "Name des Organisators",
                    "Name des Organisators kann nicht leer sein"));
        }
		
		if(!validationMessages.isEmpty()){
			this.globalFlowController.getValidationController().processValidationMessages(validationMessages);
			return false;
		}
		else{
			try {
				super.getAwk().aktualisiereTerminfindung(model.getTerminfindung(), model.getTerminfindung().getVeranstaltungName(), 
						model.getTerminfindung().getOrganisator().getName());
			} catch (TerminfindungBusinessException e) {
				LOG.error("Aktualisieren der Terminfindung fehlgeschlagen", e);				
			}
		}
		return true;
	}
}
