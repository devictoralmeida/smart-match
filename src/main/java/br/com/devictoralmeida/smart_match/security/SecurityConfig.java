package br.com.devictoralmeida.smart_match.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Precisamos desse decorator para dizer ao spring q estou sobrescrevendo as
    // config iniciais
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Desabiliando as configurações
                                                                                  // default
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/candidates").permitAll()
                            .requestMatchers("/companies").permitAll()
                            .requestMatchers("/auth/companies").permitAll()
                            .requestMatchers("/auth/candidates").permitAll();
                    auth.anyRequest().authenticated();
                });

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
