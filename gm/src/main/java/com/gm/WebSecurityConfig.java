package com.gm;

import com.gm.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthenticationFilter authenticationFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
                 .csrf().disable()
                 .authorizeRequests()
                 .antMatchers("/v2/api-docs","/configuration/ui", "/swagger-resources/**",
                         "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                 .antMatchers(HttpMethod.POST, "/register","/order","/user/subscription/*").permitAll()
                 .antMatchers(HttpMethod.GET, "/login", "/","/subscription","/box",
                         "/product","/order","/user","/user/subscription").permitAll()
                 .anyRequest().hasAuthority("ROLE_ADMIN")
                .and()
                .addFilterBefore(authenticationFilterBean(),
                        UsernamePasswordAuthenticationFilter.class);

        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("password")
                .roles("admin");
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        // First encrypt the password string
        String encodedPassword = passwordEncoder().encode("123456");

        // Set the password
        UserDetails user = User.builder()
                .username("Test")
                .password(encodedPassword)
                .roles("user")
                .build();

        // Use in-memory authentication with BCryptEncoder
        auth.inMemoryAuthentication()
                .withUser(user)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
