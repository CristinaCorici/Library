package com.sda.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/index").permitAll()
                .and().authorizeRequests().antMatchers("/static/").permitAll()
                .and().authorizeRequests().antMatchers("/login", "logout").permitAll()
                .and().authorizeRequests().antMatchers("/admin").authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable(); //H2 console runs inside a frame so we need to disable it from spring security.
    }
}

