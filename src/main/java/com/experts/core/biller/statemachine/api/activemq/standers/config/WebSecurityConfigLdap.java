package com.experts.core.biller.statemachine.api.activemq.standers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigLdap extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

            .csrf().disable()
            .headers()
            .frameOptions().disable()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .and()
            .authorizeRequests()
            .antMatchers("/", "/signin").permitAll()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated()
            .and()


            .formLogin()
            .loginPage("/swagger-ui.html").permitAll()
            .and()
            .logout()
            .permitAll()
        ;

        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
            .antMatchers(
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/security")
            .permitAll()
            .antMatchers("/selectUserName").permitAll()
            .antMatchers("/core").authenticated()
            .antMatchers("/login").permitAll()
            .antMatchers("/api/**").permitAll()
            .antMatchers("/static/**").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/ws/**").permitAll()
            .antMatchers("/ws/BillPullRequest/**").permitAll()
            .antMatchers("/ws/NotificationRequest/**").permitAll()
            .antMatchers("/ws/PrePaidRequest/**").permitAll()
            .antMatchers("/ws/PaymentRequest/**").permitAll()
            .antMatchers("/ws/InquiryRequest/**").permitAll()
            .anyRequest().permitAll();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
    }

}
