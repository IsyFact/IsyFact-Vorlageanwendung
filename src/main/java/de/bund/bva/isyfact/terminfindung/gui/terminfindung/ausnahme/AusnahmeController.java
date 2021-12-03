package de.bund.bva.isyfact.terminfindung.gui.terminfindung.ausnahme;

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

import org.springframework.webflow.engine.support.TransitionExecutingFlowExecutionExceptionHandler;
import org.springframework.webflow.execution.RequestContext;

import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.isyfact.terminfindung.common.exception.TerminfindungBusinessException;

public class AusnahmeController {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(AusnahmeController.class);

    public void initialisiereModel(AusnahmeModel model, RequestContext context) {
        TerminfindungBusinessException exc = (TerminfindungBusinessException) context.getFlashScope().get(TransitionExecutingFlowExecutionExceptionHandler.ROOT_CAUSE_EXCEPTION_ATTRIBUTE);
        model.setException(exc);
    }

}