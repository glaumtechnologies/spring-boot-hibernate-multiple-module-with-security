package com.glaum.login.configuration;

import com.glaum.login.repository.UserDao;
import com.glaum.login.util.RoleEnum;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@EnableJpaRepositories(basePackageClasses = UserDao.class)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    DataSource dataSource;

    @Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Note url configuration order matters.
        http.authorizeRequests().antMatchers("/registration/**").permitAll();
        http.authorizeRequests().antMatchers("/customer/**").hasRole(RoleEnum.ROLE_ADMIN.getRole());
        http.authorizeRequests().antMatchers("/account/**").hasRole(RoleEnum.ROLE_USER.getRole());
        http.authorizeRequests().anyRequest().authenticated()
                .and().httpBasic()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/home").failureUrl("/login?error=true").permitAll();
        http.logout().clearAuthentication(true).deleteCookies("JSESSIONID").invalidateHttpSession(true).logoutSuccessUrl("/login");
        http.exceptionHandling().accessDeniedPage("/accessdenied");
        http.csrf().and().rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(1209600);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

}
