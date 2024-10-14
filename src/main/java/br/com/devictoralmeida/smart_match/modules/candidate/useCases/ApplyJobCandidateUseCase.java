package br.com.devictoralmeida.smart_match.modules.candidate.useCases;

import br.com.devictoralmeida.smart_match.exceptions.JobNotFoundException;
import br.com.devictoralmeida.smart_match.exceptions.UserNotFoundException;
import br.com.devictoralmeida.smart_match.modules.candidate.entities.ApplyJobEntity;
import br.com.devictoralmeida.smart_match.modules.candidate.repository.ApplyJobRepository;
import br.com.devictoralmeida.smart_match.modules.candidate.repository.CandidateRepository;
import br.com.devictoralmeida.smart_match.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private ApplyJobRepository applyJobRepository;

  // Vamos precisar do ID do candidato e o ID da vaga
  public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
    // Precisamos validar se existe o candidato e a vaga
    candidateRepository.findById(idCandidate).orElseThrow(() -> {
      throw new UserNotFoundException();
    });

    jobRepository.findById(idJob).orElseThrow(() -> {
      throw new JobNotFoundException();
    });

    var applyJob = ApplyJobEntity.builder()
      .candidateId(idCandidate)
      .jobId(idJob)
      .build();

    return applyJobRepository.save(applyJob);

  }
}
