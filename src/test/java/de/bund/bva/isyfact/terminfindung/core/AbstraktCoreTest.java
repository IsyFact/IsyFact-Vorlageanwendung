package de.bund.bva.isyfact.terminfindung.core;

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
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import de.bund.bva.isyfact.terminfindung.common.konstanten.TestProfile;
import de.bund.bva.pliscommon.persistence.datetime.ZeitraumEntitaet;

/**
 * Basisklasse für Unit-Tests des Anwendungskerns.Für die Tests wird nur der Anwendungskern der Anwendung aufgebaut. Die
 * Persistenzschicht wird über Mockito-Mocks bereitgestellt und kann für Tests beliebig angepasst werden.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-core.xml" })
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@Profile(TestProfile.UNIT_TEST)
public abstract class AbstraktCoreTest {

    protected static final ZoneId zone = ZoneId.of("UTC");
    protected static final LocalDate datum = LocalDate.of(1, 1, 1);
    protected static final ZeitraumEntitaet MORGENS = new ZeitraumEntitaet(
            ZonedDateTime.of(datum, LocalTime.of(9, 0), zone),
            ZonedDateTime.of(datum, LocalTime.of(10, 0), zone),
            true);
    protected static final ZeitraumEntitaet ABENDS = new ZeitraumEntitaet(
            ZonedDateTime.of(datum, LocalTime.of(18, 0), zone),
            ZonedDateTime.of(datum, LocalTime.of(19, 0), zone),
            true);

}
