package com.example.userservice.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.IP_ADDRESS;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**")
                .access("hasIpAddress('" + IP_ADDRESS + "')")             //  IP_ADDRESS="x.x.x.x"
                .and()
                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        try {
            authenticationFilter.setAuthenticationManager(authenticationManager());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return authenticationFilter;
    }
}
