package com.br.springsecurity.repo;

import com.br.springsecurity.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository extends GenericRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    @PersistenceContext
    private EntityManager em;

    public User findByUsername(String username) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u from User u ");
        sql.append(" WHERE u.email =: username ");

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        TypedQuery<User> query = em.createQuery(sql.toString(), User.class);
        params.entrySet().forEach(p -> {
            query.setParameter(p.getKey(), p.getValue());
        });
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            return null;
        }
    }

    public Boolean existByEmail(String email){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT 1 from user  ");
        sql.append(" WHERE EXISTS (SELECT * from user where email =:email )");

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        Query query = em.createNativeQuery(sql.toString());
        params.entrySet().forEach(p -> {
            query.setParameter(p.getKey(), p.getValue());
        });
        try {
            query.getSingleResult();
            return  true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            return false;
        }
    }
}
