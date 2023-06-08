package ingemedia.proyectos.aula.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.logout.LogoutHandler;

import ingemedia.proyectos.aula.controllers.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  // private final LogoutHandler logoutHandler;
  @Autowired
  private CustomAccessDeniedHandler customAccessDeniedHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors().and().csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/api/v1/auth/authenticate").permitAll()
        .requestMatchers("/openapi/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
        .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasAnyAuthority("ADMIN", "ESTUDIANTE", "DOCENTE")
        .requestMatchers(HttpMethod.POST, "/api/v1/**").hasAnyAuthority("ADMIN", "ESTUDIANTE", "DOCENTE")
        .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasAnyAuthority("ADMIN", "ESTUDIANTE", "DOCENTE")
        .anyRequest().authenticated()
        .and().exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
