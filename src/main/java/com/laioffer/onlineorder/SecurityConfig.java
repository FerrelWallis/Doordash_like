package com.laioffer.onlineorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //authentication manager(see below)
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                //sql injection
                .usersByUsernameQuery("select email, password, enabled from customer where email = ? ")
                .authoritiesByUsernameQuery("select email, authorities from authorities where email = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http builder configurations for authorize requests and form login (see below)
        http
                .csrf().disable()
                .formLogin()
                .failureForwardUrl("/login?error=true");
        http
                .authorizeRequests()
                .antMatchers("/order/*", "/cart", "/checkout").hasAuthority("ROLE_USER")
                .anyRequest().permitAll();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }


}
