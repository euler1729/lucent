package com.lucent.backend.Security;

import com.lucent.backend.Filters.CustomAuthenticationFilter;
import com.lucent.backend.Filters.CustomAuthorizationFilter;
import com.lucent.backend.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity @RequiredArgsConstructor @Slf4j
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info("Security Filter Chain");

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManager); // Custom Authentication Filter
        authenticationFilter.setFilterProcessesUrl("/user/login");

        http.cors().and().csrf().disable();
        http
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/swagger-ui.html", "/swagger-ui/**", "/api-docs/**").permitAll()
                .antMatchers(HttpMethod.GET,"/user/login/**", "/token/refresh/**", "/org/det/**" ,"/org/published/**", "/membership/approved/**", "/donation/**", "/spending/latest/**", "/images/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/registration/**", "/user/verify/**", "/org/registration/**").permitAll()

                .antMatchers(HttpMethod.POST, "/org/publish/**","/org/unpublish/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/org/all/**").hasAuthority("ROLE_ADMIN")

                .antMatchers(HttpMethod.GET, "/membership/unapproved/**", "/org/find/**").hasAuthority("ROLE_MANAGER")
                .antMatchers(HttpMethod.POST, "/spending/request/**").hasAuthority("ROLE_MANAGER")
                .antMatchers(HttpMethod.PUT, "/org/update/**", "/membership/approve/**", "/membership/unapprove/**").hasAuthority("ROLE_MANAGER")

                .antMatchers(HttpMethod.POST, "/membership/request", "/donate", "/membership/check/**").hasAuthority("ROLE_DONOR")

                .antMatchers(HttpMethod.GET, "/user/resendcode/**", "/org/my/**").hasAnyAuthority("ROLE_DONOR", "ROLE_MANAGER")
                .anyRequest().authenticated()
                .and().authenticationManager(authenticationManager)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(authenticationFilter);

        return http.build();
    }
}
