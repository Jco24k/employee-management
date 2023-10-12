package com.proyect.employee.employee.config;

import com.proyect.employee.employee.entities.enums.EPermission;
import com.proyect.employee.employee.security.JwtAuthenticationFilter;
import com.proyect.employee.employee.security.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private  final JwtAuthenticationFilter jwtAuthenticationFilter;
    private  final UserDetailServiceImpl userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( authRequest -> authRequest
                        .requestMatchers(GET, String.format("%s/**",PathController.PERMISSION)).hasAnyAuthority(EPermission.READ_PERMIT.getCode(), EPermission.SUPER_ADMIN.getCode())
                        .requestMatchers(PATCH, String.format("%s/**",PathController.PERMISSION)).hasAnyAuthority(EPermission.UPDATE_PERMIT.getCode(), EPermission.SUPER_ADMIN.getCode())

                        .requestMatchers(GET, String.format("%s/**",PathController.USER)).hasAnyAuthority(EPermission.READ_USER.getCode(), EPermission.SUPER_ADMIN.getCode())
                        .requestMatchers(POST, String.format("%s/**",PathController.USER)).hasAnyAuthority(EPermission.CREATE_USER.getCode(), EPermission.SUPER_ADMIN.getCode())
                        .requestMatchers(PATCH, String.format("%s/**",PathController.USER)).hasAnyAuthority(EPermission.UPDATE_USER.getCode(), EPermission.SUPER_ADMIN.getCode())
                        .requestMatchers(DELETE, String.format("%s/**",PathController.USER)).hasAnyAuthority(EPermission.DELETE_USER.getCode(), EPermission.SUPER_ADMIN.getCode())

                        .requestMatchers(GET, String.format("%s/**",PathController.ROLE)).hasAnyAuthority(EPermission.READ_ROLE.getCode(), EPermission.SUPER_ADMIN.getCode())
                        .requestMatchers(POST, String.format("%s/**",PathController.ROLE)).hasAnyAuthority(EPermission.CREATE_ROLE.getCode(), EPermission.SUPER_ADMIN.getCode())
                        .requestMatchers(PATCH, String.format("%s/**",PathController.ROLE)).hasAnyAuthority(EPermission.UPDATE_ROLE.getCode(), EPermission.SUPER_ADMIN.getCode())
                        .requestMatchers(DELETE, String.format("%s/**",PathController.ROLE)).hasAnyAuthority(EPermission.DELETE_ROLE.getCode(), EPermission.SUPER_ADMIN.getCode())

                        .requestMatchers(POST,String.format("%s/**",PathController.AUTH)).permitAll()

                        .requestMatchers("/doc/**","/swagger-ui/**","/v3/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
