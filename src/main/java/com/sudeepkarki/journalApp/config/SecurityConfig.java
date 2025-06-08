//The below approach of extending WebSecurityCOnfigurerAdapter is depreciated approach for customization of spring security
//package com.sudeepkarki.journalApp.config;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void Configure(HttpSecurity http) throws Exception{
//        http.authorizeRequests().antMatchers("/hello").permitAll().anyRequest().authenticated().add().formLogin();
//    }
//
//}


//Below is the new approach using SecurityFilterChain as:
package com.sudeepkarki.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/health-check").permitAll() // Allow /hello without authentication
                        .anyRequest().authenticated()         // All other URLs require authentication
                );
//                .formLogin(
//                        form -> form      // Replaces deprecated .formLogin()
//                                .loginPage("/login")     // Optional: custom login page
//                                .permitAll()
//
//                ); // Enable form login

        return http.build();
    }
}
