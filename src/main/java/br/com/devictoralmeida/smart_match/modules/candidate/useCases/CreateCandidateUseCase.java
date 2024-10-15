package br.com.devictoralmeida.smart_match.modules.candidate.useCases;

import br.com.devictoralmeida.smart_match.exceptions.UserFoundException;
import br.com.devictoralmeida.smart_match.modules.candidate.entities.CandidateEntity;
import br.com.devictoralmeida.smart_match.modules.candidate.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCandidateUseCase {
  private final CandidateRepository candidateRepository;
  private final PasswordEncoder passwordEncoder;

  public CandidateEntity execute(CandidateEntity candidateEntity) {
    candidateRepository
      .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
      .ifPresent((user) -> {
        throw new UserFoundException();
      });

    var password = passwordEncoder.encode(candidateEntity.getPassword());
    candidateEntity.setPassword(password);
    return candidateRepository.save(candidateEntity);
  }
}
