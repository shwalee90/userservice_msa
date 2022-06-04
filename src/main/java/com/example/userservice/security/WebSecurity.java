package com.example.userservice.security;


import com.example.userservice.service.UserService;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.IP_ADDRESS;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Environment env;

    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/actuator/**").permitAll();
        http.authorizeRequests().antMatchers("/health_check/**").permitAll();
        http.authorizeRequests().antMatchers("/**")
                .hasIpAddress("127.0.0.1") // <- IP 변경
                .and()
                .addFilter(getAuthenticationFilter());

//        http.authorizeRequests().antMatchers("/**")
//                .access("hasIpAddress('" + IP_ADDRESS + "')")             //  IP_ADDRESS="x.x.x.x"
//                .and()
//                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(authenticationManager(),userService,env);
          //  authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }


    //select pwd from users where email =? ;
    // db_pwd(encrypted) == input_pwd(encrypted)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
