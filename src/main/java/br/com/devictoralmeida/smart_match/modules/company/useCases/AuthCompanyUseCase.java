package br.com.devictoralmeida.smart_match.modules.company.useCases;

import br.com.devictoralmeida.smart_match.exceptions.InvalidCredentialsException;
import br.com.devictoralmeida.smart_match.modules.company.dto.AuthCompanyDTO;
import br.com.devictoralmeida.smart_match.modules.company.dto.AuthCompanyResponseDTO;
import br.com.devictoralmeida.smart_match.modules.company.repositories.CompanyRepository;
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
public class AuthCompanyUseCase {
  private final CompanyRepository companyRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${security.token.secret}")
  private String secretKey;

  public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws InvalidCredentialsException {

    var company = companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
      () -> new UsernameNotFoundException("Invalid credentials"));


    var passwordMatches = passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());


    if (!passwordMatches) {
      throw new InvalidCredentialsException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    var expiresIn = Instant.now().plus(Duration.ofHours(24));

    var token = JWT.create().withIssuer("Smart Match")
      .withExpiresAt(expiresIn)
      .withSubject(company.getId().toString())
      .withClaim("roles", Arrays.asList("COMPANY"))
      .sign(algorithm);

    return AuthCompanyResponseDTO.builder()
      .access_token(token)
      .expires_in(expiresIn.toEpochMilli())
      .build();
  }
}
