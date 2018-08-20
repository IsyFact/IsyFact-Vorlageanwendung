package de.msg.terminfindung.persistence.dao;

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

import de.bund.bva.pliscommon.persistence.dao.Dao;
import de.msg.terminfindung.persistence.entity.AbstraktEntitaet;

/**
 * Basis-Interface alle DAOs der Anwendung. Legt den Typ des Primärschlüssels auf {@link Long} fest.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public interface AbstraktDao<T extends AbstraktEntitaet> extends Dao<T, Long> { }
