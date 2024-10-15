package br.com.devictoralmeida.smart_match.modules.candidate.useCases;

import br.com.devictoralmeida.smart_match.exceptions.InvalidCredentialsException;
import br.com.devictoralmeida.smart_match.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.devictoralmeida.smart_match.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.devictoralmeida.smart_match.modules.candidate.repository.CandidateRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class AuthCandidateUseCase {
  private final CandidateRepository candidateRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${security.token.secret.candidate}")
  private String secretKey;

  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO)
    throws InvalidCredentialsException {

    var candidate = candidateRepository.findByUsername(authCandidateRequestDTO.username()).orElseThrow(
      () -> new UsernameNotFoundException("Invalid credentials"));


    var passwordMatches = passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());


    if (!passwordMatches) {
      throw new InvalidCredentialsException();
    }


    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    var expiresIn = Instant.now().plus(Duration.ofHours(24));

    var token = JWT.create().withIssuer("Smart Match")
      .withExpiresAt(expiresIn)
      .withSubject(candidate.getId().toString())
      .withClaim("roles", Arrays.asList("CANDIDATE"))
      .sign(algorithm);

    return AuthCandidateResponseDTO.builder()
      .access_token(token)
      .expires_in(expiresIn.toEpochMilli())
      .build();
  }
}
