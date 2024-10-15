package br.com.devictoralmeida.smart_match.modules.candidate.useCases;

import br.com.devictoralmeida.smart_match.exceptions.JobNotFoundException;
import br.com.devictoralmeida.smart_match.exceptions.UserNotFoundException;
import br.com.devictoralmeida.smart_match.modules.candidate.entities.ApplyJobEntity;
import br.com.devictoralmeida.smart_match.modules.candidate.repository.ApplyJobRepository;
import br.com.devictoralmeida.smart_match.modules.candidate.repository.CandidateRepository;
import br.com.devictoralmeida.smart_match.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplyJobCandidateUseCase {
  private final CandidateRepository candidateRepository;
  private final JobRepository jobRepository;
  private final ApplyJobRepository applyJobRepository;


  public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
    candidateRepository.findById(idCandidate).orElseThrow(UserNotFoundException::new);
    jobRepository.findById(idJob).orElseThrow(JobNotFoundException::new);

    var applyJob = ApplyJobEntity.builder()
      .candidateId(idCandidate)
      .jobId(idJob)
      .build();
    return applyJobRepository.save(applyJob);
  }
}
