package de.bauhaus.digital.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session will be created or used by spring security
        .and()
            .httpBasic()
        .and()
            .authorizeRequests()
                .mvcMatchers("/api/register").permitAll()
                .mvcMatchers("/api/login").authenticated() // simple login endpoint
                .mvcMatchers("/api/users").authenticated() // explicitly disallow users-resource
                .mvcMatchers("/api/projects").permitAll()
                .anyRequest().permitAll()
        .and()
            .csrf().disable(); // disable cross site request forgery, as we don't use cookies - otherwise ALL PUT, POST, DELETE will get HTTP 403!
    }
}
