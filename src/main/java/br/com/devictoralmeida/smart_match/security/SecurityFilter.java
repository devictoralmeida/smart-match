package br.com.devictoralmeida.smart_match.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.devictoralmeida.smart_match.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // Anotation para que o spring consiga gerenciar o seu ciclo de vida
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, // 1º parâmetro é o request
            HttpServletResponse response, // 2º parâmetro é a response
            FilterChain filterChain) // 3º parâmetro é a cadeia de filtros
            throws ServletException, IOException {

        // Resetando o contexto das requisições
        // SecurityContextHolder.getContext().setAuthentication(null);

        // Pegando o conteúdo dentro de authorization do header
        String header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/companies")) {
            if (header != null) {
                // Aqui vamos validar o token recebido
                var token = this.jwtProvider.validateToken(header);

                // Se der erro, vamos retornar uma string vazia

                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                // Se der tudo certo vamos colocar o company_id na request.
                request.setAttribute("company_id", token.getSubject());


                // Criando array de obj roles
                var roles = token.getClaim("roles").asList(Object.class);

                // Preciso do stream para poder usar o map e gerar um novo array criando um Authority para cada role.
                var grants = roles.stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()) // Preciso ter esse prefixo ROLE_
                ).toList();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),
                        null, grants);

                // Vamos precisar injetar esse auth dentro do spring security
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response); // Aqui é tipo o next() do express
    }
}
