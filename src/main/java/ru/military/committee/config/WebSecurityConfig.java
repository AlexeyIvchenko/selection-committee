package ru.military.committee.config;

import ru.military.committee.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Тема ВКР: "Разработка информационной системы для организации работы приеной комиссии военного училища".
 * Выполнил: Ивченко А.Д., специальность - 09.05.01, группа - 4414.
 * Руководитель ВКР: Пруцков А.В., д-р техн. наук, проф. каф. ВПМ.
 * Средства разработки: Java 8, Spring Boot, СУБД MySQL, Bootstrap 3.
 * Класс предназначен для настройки конфигурации безопасности веб-приложения.
 * Дата разработки: 17.03.19.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    /**
     *  Метод создает и возвращает объект для шифрования.
     * @return Возвращает объект, реализующий шифрование данных.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Метод задает конфигурацию безопасности веб-приложения.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll()
                .antMatchers("/user/**").access("hasAuthority('ROLE_USER')")
                .antMatchers("/admin/**").access("hasAuthority('ROLE_ADMIN')")
                .and().exceptionHandling().accessDeniedPage("/403");
        http.authorizeRequests().and().formLogin()//
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")//
                .defaultSuccessUrl("/user/info")//
                .failureUrl("/login?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
    }
}