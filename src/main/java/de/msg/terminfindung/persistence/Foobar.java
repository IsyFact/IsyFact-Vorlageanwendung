package de.msg.terminfindung.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(SomeIdClass.class)
public class Foobar {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int intKey;

    @Id
    private long longKey;

    private int foo;


    public int getFoo() {
        return foo;
    }

    public void setFoo(int foo) {
        this.foo = foo;
    }
}
