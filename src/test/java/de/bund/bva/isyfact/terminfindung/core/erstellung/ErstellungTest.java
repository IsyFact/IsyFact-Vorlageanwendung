package de.bund.bva.isyfact.terminfindung.core.erstellung;

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


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import de.bund.bva.isyfact.terminfindung.common.exception.TerminfindungBusinessException;
import de.bund.bva.isyfact.terminfindung.core.AbstraktCoreTest;
import de.bund.bva.isyfact.terminfindung.core.erstellung.impl.ErstellungImpl;
import de.bund.bva.isyfact.terminfindung.persistence.dao.TerminfindungDao;
import de.bund.bva.isyfact.terminfindung.persistence.entity.Tag;
import de.bund.bva.isyfact.terminfindung.persistence.entity.Terminfindung;
import de.bund.bva.isyfact.terminfindung.persistence.entity.Zeitraum;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Test für den Anwendungsfall "Terminfindung erstellen".
 *
 * @author msg systems ag
 */
public class ErstellungTest extends AbstraktCoreTest {

    private static final Long TERMINFINDUNG_ID = 4711L;

    private TerminfindungDao terminfindungDao;

    private Terminfindung tf;

    @Before
    public void init() {
        terminfindungDao = mock(TerminfindungDao.class);

        // Terminfindung-DAO-Mock konfigurieren
        doAnswer((Answer<Void>) invocation -> {
            tf = invocation.getArgumentAt(0, Terminfindung.class);
            tf.setId(TERMINFINDUNG_ID);
            return null;
        }).when(terminfindungDao).speichere(any(Terminfindung.class));

        doAnswer((Answer<Terminfindung>) invocation -> tf).when(terminfindungDao).sucheMitId(TERMINFINDUNG_ID);
    }

    @Test(expected = TerminfindungBusinessException.class)
    public void erstelleTerminfindungTestOrgNameNull() throws TerminfindungBusinessException {
        Erstellung erstellung = new ErstellungImpl(terminfindungDao);
        erstellung.erstelleTerminfindung(null, "Veranstaltung", new ArrayList<Tag>());
    }

    @Test(expected = TerminfindungBusinessException.class)
    public void erstelleTerminfindungTestVeranstNameNull() throws TerminfindungBusinessException {
        Erstellung erstellung = new ErstellungImpl(terminfindungDao);
        erstellung.erstelleTerminfindung("Organisation", null, new ArrayList<Tag>());
    }

    @Test(expected = TerminfindungBusinessException.class)
    public void erstelleTerminfindungTestTerminListeNull() throws TerminfindungBusinessException {
        Erstellung erstellung = new ErstellungImpl(terminfindungDao);
        erstellung.erstelleTerminfindung("Organisation", "Veranstaltung", null);
    }

    @Test(expected = TerminfindungBusinessException.class)
    public void erstelleTerminfindungTestTerminListeLeer() throws TerminfindungBusinessException {
        Erstellung erstellung = new ErstellungImpl(terminfindungDao);
        erstellung.erstelleTerminfindung("Organisation", "Veranstaltung", new ArrayList<Tag>());
    }

    @Test
    public void testErstelleTerminfindung() throws Exception {
        Erstellung erstellung = new ErstellungImpl(terminfindungDao);

        List<Tag> termine = new ArrayList<>();
        Tag t1 = new Tag(LocalDate.of(2100, 1, 1));
        List<Zeitraum> zeitraeume = new ArrayList<>();
        zeitraeume.add(new Zeitraum(MORGENS));
        zeitraeume.add(new Zeitraum(ABENDS));
        t1.setZeitraeume(zeitraeume);
        termine.add(t1);

        Tag t2 = new Tag(LocalDate.of(2100, 1, 2));
        zeitraeume = new ArrayList<>();
        zeitraeume.add(new Zeitraum(MORGENS));
        zeitraeume.add(new Zeitraum(ABENDS));
        t2.setZeitraeume(zeitraeume);
        termine.add(t2);

        Terminfindung terminfindung = erstellung.erstelleTerminfindung("Max", "Grillfest 2015", termine);
        assertNotNull(terminfindung);

        Long tfId = terminfindung.getId();
        assertNotNull(tfId);

        terminfindung = terminfindungDao.sucheMitId(tfId);

        assertNotNull(terminfindung.getTermine());
        assertEquals(2, terminfindung.getTermine().size());

        zeitraeume = terminfindung.getTermine().get(0).getZeitraeume();
        assertNotNull(zeitraeume);
        assertEquals(2, zeitraeume.size());
        assertNotNull(zeitraeume.get(0).getZeitraum());
    }

}
