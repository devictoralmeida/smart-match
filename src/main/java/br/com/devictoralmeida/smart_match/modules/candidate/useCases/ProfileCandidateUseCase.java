package br.com.devictoralmeida.smart_match.modules.candidate.useCases;

import br.com.devictoralmeida.smart_match.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.devictoralmeida.smart_match.modules.candidate.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileCandidateUseCase {
  private final CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID candidateId) {
    var candidate = candidateRepository.findById(candidateId).orElseThrow(
      () -> new UsernameNotFoundException("User not found"));
    return ProfileCandidateResponseDTO.builder()
      .name(candidate.getName())
      .description(candidate.getDescription())
      .email(candidate.getEmail())
      .username(candidate.getUsername())
      .id(candidate.getId())
      .build();
  }
}
