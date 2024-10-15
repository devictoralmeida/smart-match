package br.com.devictoralmeida.smart_match.modules.company.useCases;

import br.com.devictoralmeida.smart_match.exceptions.CompanyNotFoundException;
import br.com.devictoralmeida.smart_match.modules.company.entities.JobEntity;
import br.com.devictoralmeida.smart_match.modules.company.repositories.CompanyRepository;
import br.com.devictoralmeida.smart_match.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateJobUseCase {
  private final JobRepository jobRepository;
  private final CompanyRepository companyRepository;

  public JobEntity execute(JobEntity jobEntity) {
    companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
    return jobRepository.save(jobEntity);
  }
}
