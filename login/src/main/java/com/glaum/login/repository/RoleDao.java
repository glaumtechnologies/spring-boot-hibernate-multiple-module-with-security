package com.glaum.login.repository;

import com.glaum.login.entity.Role;
import com.glaum.login.entity.User;
import com.glaum.login.util.RoleEnum;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;

@Transactional
@Repository
public class RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Role find(RoleEnum role) {
        Query query = entityManager.createQuery("from Role where role = ?");
        query.setParameter(1, role.name());
        Optional<Role> optional = query.getResultList().stream().findFirst();
        return optional.get();
    }



}
