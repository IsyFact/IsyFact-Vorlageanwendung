package de.msg.terminfindung.core.erstellung.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Erstellung Monitor")
public class ErstellungMBean {

    private int foo;

    @ManagedAttribute(description = "Wert von foo")
    public int getFoo() {
        return foo;
    }

    public void setFoo(int foo) {
        this.foo = foo;
    }
}
