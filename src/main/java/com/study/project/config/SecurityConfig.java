package com.study.project.config;

import com.study.project.account.AccountService;
import com.study.project.account.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final AccountService accountService;
    private final DataSource dataSource;
    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/node_modules/**", "/sign-up", "/check-email-token", "/email-login", "/check-email-login", "/login-link", "/static/**", "/images/**").permitAll()
                        .anyRequest().authenticated())
                .headers(headers -> headers
                        .contentTypeOptions(HeadersConfigurer.ContentTypeOptionsConfig::disable) // MIME 타입 필터링 해제
                )
                .addFilterBefore(new SecurityContextPersistenceFilter(), BasicAuthenticationFilter.class);
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(formLogin -> formLogin.loginPage("/login").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/"));

        http.rememberMe(r ->
                r.userDetailsService(accountService)
                        .tokenRepository(tokenRepository()));


        return http.build();
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/node_modules/**")
                .addResourceLocations("file:src/main/resources/static/node_modules/")
                .setCachePeriod(3600)
                .resourceChain(true);

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:src/main/resources/static/images/")
                .setCachePeriod(3600)
                .resourceChain(true);
    }

}
