package br.com.devictoralmeida.smart_match.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class TestUtils {

  public static String objectToJson(Object obj) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String generateToken(UUID idCompany, String secret) {

    Algorithm algorithm = Algorithm.HMAC256(secret);

    Instant expiresIn = Instant.now().plus(Duration.ofHours(24));

    String token = JWT.create().withIssuer("Smart Match")
      .withExpiresAt(expiresIn)
      .withSubject(idCompany.toString())
      .withClaim("roles", Arrays.asList("COMPANY"))
      .sign(algorithm);
    return token;
  }
}
