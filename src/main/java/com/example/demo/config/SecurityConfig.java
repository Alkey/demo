package com.example.demo.config;

import com.example.demo.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final String adminPassword;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(@Value("#{environment.PASSWORD}") String adminPassword,
                          UserDetailsService userDetailsService) {
        this.adminPassword = adminPassword;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .formLogin()
                .defaultSuccessUrl("/hello.html", true)
                .and()
                .logout()
                .and()
                .authorizeRequests()
                .antMatchers("/clients/add").permitAll()
                .antMatchers("/clients/set-role/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/products/*").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/products").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/products/*").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode(adminPassword))
                .roles(Role.ADMIN.name());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
