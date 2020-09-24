package com.glaum.login.repository;

import static com.glaum.login.util.RoleEnum.ROLE_ADMIN;
import static com.glaum.login.util.RoleEnum.ROLE_USER;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.glaum.login.entity.permission;


@Transactional
@Repository
public class permissionDAO {
	@PersistenceContext
	private EntityManager entityManager;



	    public permission findpermissionidByname(String name) {
	        Query query = entityManager.createQuery("from permission where name = ?");
	        query.setParameter(1, name.toLowerCase());
	        Optional<permission> optional = query.getResultList().stream().findFirst();

	        return optional.orElse(null);
	    }
	    
	    public List<permission> findpermissionid() {
	        Query query = entityManager.createQuery("from permission");

	        List<permission> listobj =(List<permission>) query.getResultList().stream().collect(Collectors.toList());

	        return listobj;
	    }
	    
	    

	  
}
