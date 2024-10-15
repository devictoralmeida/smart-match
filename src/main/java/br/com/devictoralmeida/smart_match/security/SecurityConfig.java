package br.com.devictoralmeida.smart_match.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private static final String[] SWAGGER_LIST = {
    "/swagger-ui/**",
    "/v*/api-docs/**",
    "/swagger-resources/**"
  };

  private final SecurityFilter securityFilter;
  private final SecurityCandidateFilter securityCandidateFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth -> {
        auth.requestMatchers("/candidates").permitAll()
          .requestMatchers("/companies").permitAll()
          .requestMatchers("/companies/auth").permitAll()
          .requestMatchers("/candidates/auth").permitAll()
          .requestMatchers(SWAGGER_LIST).permitAll();
        auth.anyRequest().authenticated();
      })

      .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
      .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
