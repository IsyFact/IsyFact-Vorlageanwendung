package de.bund.bva.isyfact.terminfindung.gui.administration;

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
import java.util.List;

import de.bund.bva.isyfact.terminfindung.gui.terminfindung.AbstractModel;
import de.bund.bva.isyfact.terminfindung.gui.terminfindung.model.TerminfindungModel;

/**
 * Model für Administrationsseiten.
 *
 * @author msg systems ag, Dirk Jäger
 */
public class AdminModel extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<TerminfindungModel> alleTerminfindungen;

    public void setAlleTerminfindungen(List<TerminfindungModel> alleTerminfindungen) {
        this.alleTerminfindungen = alleTerminfindungen;
    }

    public List<TerminfindungModel> getAlleTerminfindungen() {
        return alleTerminfindungen;
    }

}
