package br.com.devictoralmeida.smart_match.modules.candidate.useCases;

import br.com.devictoralmeida.smart_match.exceptions.JobNotFoundException;
import br.com.devictoralmeida.smart_match.exceptions.UserNotFoundException;
import br.com.devictoralmeida.smart_match.modules.candidate.CandidateEntity;
import br.com.devictoralmeida.smart_match.modules.candidate.CandidateRepository;
import br.com.devictoralmeida.smart_match.modules.candidate.entities.ApplyJobEntity;
import br.com.devictoralmeida.smart_match.modules.candidate.repository.ApplyJobRepository;
import br.com.devictoralmeida.smart_match.modules.company.entities.JobEntity;
import br.com.devictoralmeida.smart_match.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplyJobCandidateUseCaseTest {
  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private ApplyJobRepository applyJobRepository;

  @Test
  @DisplayName("Should not be able to apply job with candidate not found")
  void should_not_be_able_to_apply_job_with_candidate_not_found() {
    try {
      applyJobCandidateUseCase.execute(null, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UserNotFoundException.class);
    }
  }

  @Test
  @DisplayName("Should not be able to apply job with job not found")
  void should_not_be_able_to_apply_job_with_job_not_found() {
    var idCandidate = UUID.randomUUID();

    var candidate = new CandidateEntity();
    candidate.setId(idCandidate);


    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

    try {
      applyJobCandidateUseCase.execute(idCandidate, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(JobNotFoundException.class);
    }
  }

  @Test
  @DisplayName("Should be able to create a new apply job")
  void should_be_able_to_create_a_new_aplly_job() {
    var idCandidate = UUID.randomUUID();
    var idJob = UUID.randomUUID();

    var applyJob = ApplyJobEntity.builder().candidateId(idCandidate)
      .jobId(idJob).build();

    var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
    when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

    when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

    var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

    assertThat(result).hasFieldOrProperty("id");
    assertNotNull(result.getId());
  }
}
