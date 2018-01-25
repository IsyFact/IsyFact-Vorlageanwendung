package de.msg.terminfindung.core.erstellung;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Erstellung Monitor")
public class ErstellungMonitor {

    private int foo;

    private Erstellung erstellung;

    @ManagedAttribute(description = "Wert von foo")
    public int getFoo() throws TerminfindungBusinessException{
        erstellung.erstelleTerminfindung(null, null, null);

        return foo;
    }

    public void setFoo(int foo) {
        this.foo = foo;
    }
}
