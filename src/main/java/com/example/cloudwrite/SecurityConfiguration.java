package com.example.cloudwrite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder)
                .withUser("admin").password(passwordEncoder.encode("admin123")).roles("USER", "ADMIN")
                .and()
                .withUser("user").password(passwordEncoder.encode("user123")).roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // this should be removed at production (csrf disabled for h2-console only)
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and().csrf().ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin();

        http.authorizeRequests()
                //set pages which do not require authentication
                .antMatchers("/", "/welcome", "/login").permitAll()
                //set pages which require authentication
                .antMatchers("/authenticated/**").hasAnyRole("ADMIN", "USER")
                // handle admin pages
                .antMatchers("/adminPage").hasAnyRole("ADMIN")
                .and().formLogin()
                .and().logout().logoutSuccessUrl("/welcome").permitAll()
                .and().csrf().disable();
    }
}
