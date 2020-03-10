package de.bund.bva.isyfact.terminfindung.gui.util;

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

import java.util.UUID;

import com.github.dozermapper.core.CustomConverter;
import com.github.dozermapper.core.MappingException;

/**
 * Dozer Custom Converter zwischen {@link UUID} und {@link String}.
 */
public class UuidStringDozerCustomConverter implements CustomConverter {

	@Override
	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		if (sourceFieldValue == null) {
			return null;
		}

		// String -> UUID
		if (sourceFieldValue instanceof String) {
			try {
				return convertStringToUUID((String) sourceFieldValue);
			} catch (IllegalArgumentException e) {
				throw new MappingException(e.getMessage());
			}
		// UUID -> String
		} else if (sourceFieldValue instanceof UUID) {
			return convertUUIDtoString((UUID) sourceFieldValue);
		} else {
			throw new MappingException("UuidStringDozerCustomConverter used incorrectly. Arguments passed in were:"
									   + existingDestinationFieldValue + " and " + sourceFieldValue);
		}
	}

	private UUID convertStringToUUID(String s) {
		return UUID.fromString(s);
	}

	private String convertUUIDtoString(UUID u) {
		return u.toString();
	}
}
