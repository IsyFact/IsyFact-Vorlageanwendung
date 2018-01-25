package de.msg.terminfindung.persistence.dao.impl;

import javax.persistence.EntityManager;

import de.msg.terminfindung.persistence.dao.SomeDao;

public class SomeDaoImpl implements SomeDao {

    private EntityManager em;

    @Override
    public int bulkUpdate() {
        return em.createQuery("UPDATE Employee e " +
            "SET e.manager = ?1 " +
            "WHERE e.department = ?2")
            .setParameter(1, "foo")
            .setParameter(2, "bar")
            .executeUpdate();
    }
}
