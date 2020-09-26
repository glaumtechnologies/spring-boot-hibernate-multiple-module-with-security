package com.glaum.login.service;

import com.glaum.login.entity.Role;
import com.glaum.login.entity.User;
import com.glaum.login.entity.Permission;
import com.glaum.login.repository.PermissionDAO;
import com.glaum.login.repository.UserDao;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Autowired
    PermissionDAO permissionDao;
    
    @Autowired
    HttpSession httpsessionobj;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        List<GrantedAuthority> roles = Lists.newArrayList();
        
       roles.add(new SimpleGrantedAuthority(String.valueOf(user.getRoleId())));
       for(Role role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
            
        }
       
       List<Permission> listofpermissionobj=permissionDao.getPermissions();
       
       Map<String, Integer> mapofuserpermission=userDao.findUserPermission(user.getId());
       
       httpsessionobj.setAttribute("userPermissions", listofpermissionobj);
       httpsessionobj.setAttribute("userRole", user.getRoleId());
       httpsessionobj.setAttribute("userpermissiondetails", mapofuserpermission);
       
       

      
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                true, true, true, true, roles);
    }
}
