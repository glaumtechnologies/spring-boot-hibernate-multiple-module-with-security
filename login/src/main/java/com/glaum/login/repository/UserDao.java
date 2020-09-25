package com.glaum.login.repository;

import com.glaum.login.entity.Role;
import com.glaum.login.entity.User;
import com.glaum.login.util.RoleEnum;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.glaum.login.util.RoleEnum.ROLE_ADMIN;
import static com.glaum.login.util.RoleEnum.ROLE_USER;

@Transactional
@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RoleDao roleDao;

    public User findUserByUsername(String username) {
        Query query = entityManager.createQuery("from User where username = ?");
        query.setParameter(1, username.toLowerCase());
        Optional<User> optional = query.getResultList().stream().findFirst();

        return optional.orElse(null);
    }

    public int createUser(com.glaum.login.model.User user) {
        return create(user, ROLE_USER);
    }

    public int creatAdmin(com.glaum.login.model.User user) {
        return create(user, ROLE_ADMIN);
    }
    
    public Map<String, Integer> findUserPermission(int userid) {
       Query query = entityManager.createNativeQuery("select p.bit,p.name  \n"
       		+ "   FROM user u  LEFT JOIN Permission p  ON u.role_id & p.bit\n"
       		+ " WHERE u.id =?");
        query.setParameter(1,userid);
        List<Object[]> resultList = query.getResultList();
        Map<String,Integer> mapofpermission= new HashMap<String, Integer>();
        for (Object[] object : resultList){
        	
            String key = (String)object[1];
            int value = (int) object[0];
            mapofpermission.put(key,value);
        	}
        return mapofpermission;
       
    }


    private int create(com.glaum.login.model.User user, RoleEnum roleEnum) {
        User userEntity = new User();
        userEntity.setEmail(user.getEmail());
        userEntity.setUsername(user.getUsername().toLowerCase());
        userEntity.setPassword(user.getPassword());
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleDao.find(roleEnum));
        userEntity.setRoles(roles);
        entityManager.unwrap(Session.class).save(userEntity);
        return userEntity.getId();
    }
}
