package org.example.lab12.SecurityConfig;

import lombok.RequiredArgsConstructor;
import org.example.lab12.Service.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailService myUserDetailService;


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()// ثغره هجمه عمليات معينه ع حساب المستخدم
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests() // api
                .requestMatchers("/api/v1/user/register","/api/v1/user/login").permitAll()
                .requestMatchers("/api/v1/user/get-all-user","/api/v1/user/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/blog/**","/api/v1/user/update/").authenticated()
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout").permitAll()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();

// permitAll للكل نفس صفحه الهوم
// hasAuthority مثل حذف يوزر ف احدد ادمن لان هو يقدر
// hasAnyAuthority احدد اكثر من رول شخص
//  نحط نجمتين يحدد كلشي اذا عندي ملف auth كامل لشخص "/api/v1/auth/**"

    }}