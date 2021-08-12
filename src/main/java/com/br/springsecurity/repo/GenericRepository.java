package com.br.springsecurity.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public class GenericRepository {

    @PersistenceContext
    protected EntityManager em;

    public void delete(Object entity) {
        em.remove(entity);
    }

    public <T> T persist(T entity) {
        em.persist(entity);
        em.flush();
        return entity;
    }

    public <T> T merge(T entity) {
        em.merge(entity);
        em.flush();
        return entity;
    }

    public <T> List<T> list(Class<T> clazz) {
        String hqlList = new StringBuilder().append(" from ").append(clazz.getSimpleName()).toString();
        return em.createQuery(hqlList, clazz).getResultList();
    }

    public <T> T update(T entity) {
        em.merge(entity);
        em.flush();
        return entity;
    }

    public <T> T find(Class<T> clazz, Serializable id) {
        return em.find(clazz, id);
    }

}
