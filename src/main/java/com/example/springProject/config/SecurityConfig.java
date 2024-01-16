package com.example.springProject.config;

import com.example.springProject.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers(HttpMethod.GET, "/", "/login", "/register", "/error", "/login-error", "/logout", "/css/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/createUser").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/user").hasAuthority("USER");
                    authConfig.requestMatchers(HttpMethod.GET, "clientPage").hasAuthority("USER");
                    authConfig.requestMatchers(HttpMethod.GET, "/admin").hasAuthority("ADMIN");
                    authConfig.requestMatchers(HttpMethod.GET, "/developer").hasAuthority("DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority("ADMIN", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.GET, "/authorities").hasAnyAuthority("ADMIN", "DEVELOPER");
                    authConfig.anyRequest().authenticated();
                })
                .formLogin(login -> {
                            login.loginPage("/login");
                            login.successHandler(successHandler());
                            login.failureUrl("/login-error");
                        }
                )

                .logout(logout -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
                    logout.logoutSuccessUrl("/");
                    logout.deleteCookies("JSESSIONID");
                    logout.invalidateHttpSession(true);
                });

        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

                if (roles.contains("USER")) {
                    response.sendRedirect("clientPage");
                } else if (roles.contains("ADMIN")) {
                    response.sendRedirect("/");
                } else if (roles.contains("DEVELOPER")) {
                    response.sendRedirect("employeePage");
                }
            }
        };
    }
    @Bean
    UserDetailsService myUserDetailsService(UserRepository userRepository) {
        return new MyUserDetailsService(userRepository);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successEvent() {
        return event -> {
            System.out.println("Success Login " + event.getAuthentication().getClass().getSimpleName() + " - " + event.getAuthentication().getName());
        };
    }

    @Bean
    ApplicationListener<AuthenticationFailureBadCredentialsEvent> failureEvent() {
        return event -> {
            System.err.println("Bad Credentials Login " + event.getAuthentication().getClass().getSimpleName() + " - " + event.getAuthentication().getName());
        };
    }
}