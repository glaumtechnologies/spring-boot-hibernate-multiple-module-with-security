package com.glaum.dao;

import com.glaum.entity.GmanageTest;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@Repository
public class GmanageTestDaoImpl {

    public static Logger logger = LoggerFactory.getLogger(GmanageTestDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void getGmanageTest() {
        Criteria criteria = entityManager.unwrap(Session.class).createCriteria(GmanageTest.class);
        logger.info(criteria.list().toString());
    }

    public void updateGmanageTest() {
        GmanageTest gmanageTest = new GmanageTest();
        gmanageTest.setUsername("gmanage");
        entityManager.merge(gmanageTest);
        entityManager.flush();
    }

}
