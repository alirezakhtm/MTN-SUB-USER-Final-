/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.mtn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
/**
 *
 * @author alirzea
 */
@EnableWebSecurity
@Configuration
public class SecurityController extends WebSecurityConfigurerAdapter{
    
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("alirezakhtm")
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("alireza@2413"))
                .roles("USER", "ADMIN");
    }
    
    
}
