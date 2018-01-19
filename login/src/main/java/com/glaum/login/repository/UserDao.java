package com.glaum.login.repository;

import com.glaum.login.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean findUserByUsernameAndPassword(String username, String password) {
        Query query = entityManager.createQuery("from User where username = ? and password = ?");
        query.setParameter(1, username);
        query.setParameter(2, password);
        Optional<User> optional = query.getResultList().stream().findFirst();
        return optional.isPresent();
    }

}
