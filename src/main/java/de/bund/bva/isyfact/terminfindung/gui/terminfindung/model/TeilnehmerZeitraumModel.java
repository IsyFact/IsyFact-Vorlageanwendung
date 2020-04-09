package de.bund.bva.isyfact.terminfindung.gui.terminfindung.model;

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

public class TeilnehmerZeitraumModel implements Serializable {

    private long id;

    private PraeferenzModel praeferenz;

    private static final long serialVersionUID = 1L;

    TeilnehmerModel teilnehmer;

    ZeitraumModel zeitraum;

    public TeilnehmerZeitraumModel() {
        super();
    }

    public TeilnehmerZeitraumModel(TeilnehmerModel teilnehmer, PraeferenzModel praeferenz) {
        this.teilnehmer = teilnehmer;
        this.praeferenz = praeferenz;
    }

    public PraeferenzModel getPraeferenz() {
        return this.praeferenz;
    }

    public void setPraeferenz(PraeferenzModel praeferenz) {
        this.praeferenz = praeferenz;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TeilnehmerModel getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(TeilnehmerModel teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public ZeitraumModel getZeitraum() {
        return zeitraum;
    }

    public void setZeitraum(ZeitraumModel zeitraum) {
        this.zeitraum = zeitraum;
    }

}
