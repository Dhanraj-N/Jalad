package com.franchiseworld.jalad.config;


import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.franchiseworld.jalad.serviceimplementation.UserDetailsServiceImpl;
@Configuration
@EnableWebSecurity
public class   SecurityConfig {
    @Autowired
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private SecurityProperties securityProperties;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(RestAuthenticationEntryPoint authenticationEntryPoint,
                          UserDetailsServiceImpl userDetailsServiceImpl) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF, enable CORS, configure URL access rules
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/personal/**").hasRole("PERSONAL_USER")
                        .requestMatchers("/api/business/**").hasRole("BUSINESS_USER")
                        .requestMatchers("/api/zone/**").hasRole("ZONE")// Open endpoints for authentication
                        .requestMatchers("/auth/authenticate","/auth/signup/**").permitAll()
                        .anyRequest().authenticated())  // All other requests need authentication
                        .httpBasic(withDefaults())
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

@Bean
public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsServiceImpl);
    authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
    return authProvider;
}



@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
}
}
