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
                 .antMatchers("/console/**").permitAll()
                 .antMatchers(HttpMethod.POST, "/register","/order","/user/subscription/*").permitAll()
                 .antMatchers(HttpMethod.GET, "/login", "/","/subscription","/box",
                         "/product","/order","/user","/user/subscription").permitAll()

                 .anyRequest().hasAuthority("ROLE_ADMIN")
                .and()
                .addFilterBefore(authenticationFilterBean(),
                        UsernamePasswordAuthenticationFilter.class);

        http.headers().cacheControl();
        http.headers().frameOptions().disable();
    }
}
