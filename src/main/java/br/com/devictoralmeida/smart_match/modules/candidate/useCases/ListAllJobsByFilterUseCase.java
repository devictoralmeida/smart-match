package br.com.devictoralmeida.smart_match.modules.candidate.useCases;

import br.com.devictoralmeida.smart_match.modules.company.entities.JobEntity;
import br.com.devictoralmeida.smart_match.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListAllJobsByFilterUseCase {
  private final JobRepository jobRepository;

  public List<JobEntity> execute(String filter) {
    return jobRepository.findByDescriptionContainingIgnoreCase(filter);
  }
}
