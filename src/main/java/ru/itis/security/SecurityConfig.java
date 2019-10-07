package ru.itis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // включаем проверку безопасности через аннотации
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private TokenAuthenticationProvider provider;

    // конфигурируем AuthenticationManager
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // прикручиваем наш провайдер
        auth.authenticationProvider(provider);
    }
    // конфигурирем саму безопасность
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // отключаем csrf
        http.csrf().disable();
        // отключаем сессии
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // добавляем наш фильтр
        http.addFilterBefore(new TokenAuthenticationFilter(), BasicAuthenticationFilter.class);
        // говорим, что разрешаем Swagger
        http.authorizeRequests().antMatchers("/swagger-ui.html#/**").permitAll();
    }

}

