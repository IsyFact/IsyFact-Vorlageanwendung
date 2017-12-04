package de.msg.terminfindung.gui.util;

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
import java.time.ZoneId;
import java.time.ZonedDateTime;

import de.bund.bva.isyfact.datetime.core.Zeitraum;
import de.bund.bva.isyfact.datetime.persistence.ZeitraumEntitaet;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 * Dozer Custom Converter für {@link Zeitraum} und {@link ZeitraumEntitaet}.
 */
public class ZeitraumDozerCustomConverter implements CustomConverter {

	@Override
	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		if (sourceFieldValue == null) {
			return null;
		}

		if (sourceFieldValue instanceof ZeitraumEntitaet) {
			return Zeitraum.of((ZeitraumEntitaet) sourceFieldValue);
		} else if (sourceFieldValue instanceof Zeitraum) {
			Zeitraum source = (Zeitraum)sourceFieldValue;

			if (source.isOhneDatum()) {
                ZonedDateTime anfang = ZonedDateTime.of(LocalDate.now(), source.getAnfangszeit(), ZoneId.of("UTC"));
                ZonedDateTime ende = ZonedDateTime.of(LocalDate.now(), source.getEndzeit(), ZoneId.of("UTC"));

				return new ZeitraumEntitaet(anfang, ende, true);
			} else {
				return new ZeitraumEntitaet(source.getAnfangsdatumzeit(), source.getEndedatumzeit(), false);
			}
		} else {
			throw new MappingException("ZeitraumDozerCustomConverter used incorrectly. Arguments passed in were:"
									   + existingDestinationFieldValue + " and " + sourceFieldValue);
		}
	}
}
