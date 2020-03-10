package de.bund.bva.isyfact.terminfindung.task;

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

import de.bund.bva.isyfact.task.TaskScheduler;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;

public class StartStopTasks {

    private final TaskScheduler taskScheduler;

    public StartStopTasks(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void starteTasks() {
        taskScheduler.starteKonfigurierteTasks();
    }

    @EventListener(ContextStoppedEvent.class)
    public void stoppeTasks() throws InterruptedException {
        System.out.println("shutdown");
        taskScheduler.shutdownMitTimeout(5);
    }
}
