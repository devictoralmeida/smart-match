package br.com.devictoralmeida.smart_match.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.devictoralmeida.smart_match.exceptions.InvalidCredentialsException;
import br.com.devictoralmeida.smart_match.modules.candidate.CandidateRepository;
import br.com.devictoralmeida.smart_match.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.devictoralmeida.smart_match.modules.candidate.dto.AuthCandidateResponseDTO;


@Service
public class AuthCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO)
            throws InvalidCredentialsException {
        // Verificar se existe o candidato pelo username
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("Invalid credentials");
                });

        // Verificar se as senhas são iguais
        var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

        // Se for diferente --> Erro
        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        // Se der ok --> Gerar token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(24));

        var token = JWT.create().withIssuer("Smart Match")
                .withExpiresAt(expiresIn)
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("CANDIDATE")) // Criando a role do usuário
                .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return authCandidateResponse;
    }
}
