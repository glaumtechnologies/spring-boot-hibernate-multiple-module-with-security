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

import com.glaum.login.entity.Permission;



@Transactional
@Repository
public class PermissionDAO {
	@PersistenceContext
	private EntityManager entityManager;



	    public Permission findPermissionByName(String name) {
	        Query query = entityManager.createQuery("from Permission where name = ?");
	        query.setParameter(1, name.toLowerCase());
	        Optional<Permission> optional = query.getResultList().stream().findFirst();

	        return optional.orElse(null);
	    }
	    
	    public List<Permission> getPermissions() {
	        Query query = entityManager.createQuery("from Permission");

	        List<Permission> listobj =(List<Permission>) query.getResultList().stream().collect(Collectors.toList());

	        return listobj;
	    }
	    
	    

	  
}
