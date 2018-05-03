package com.example.tweethibernate.configuration;

import com.example.tweethibernate.security.AuthSuccessHandler;
import com.example.tweethibernate.security.RestAuthEntryPoint;
import com.example.tweethibernate.security.SessionInterceptor;
import com.example.tweethibernate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {


    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(new Md4PasswordEncoder());

        return auth;
    }

    @Configuration
    @ConditionalOnProperty(name = "security.enabled", havingValue = "spring")
    public static class LoginConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private DaoAuthenticationProvider provider;

        @Autowired
        private RestAuthEntryPoint entryPoint;

        @Autowired
        private AuthSuccessHandler successHandler;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("user")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler()) // need this to disable redirect
                .and()
                .logout();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(provider);
        }
    }

    @Configuration
    @ConditionalOnProperty(name = "security.enabled", havingValue = "manual")
    public static class SessionInterceptorConfiguration implements WebMvcConfigurer {
        @Autowired
        private SessionInterceptor sessionInterceptor;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(sessionInterceptor);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new Md4PasswordEncoder();
        }

        @Bean
        public Map<String, Principal> sessions() {
            return new HashMap<>();
        }
    }
}
