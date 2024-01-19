package br.com.devictoralmeida.smart_match.modules.company.useCases;

import br.com.devictoralmeida.smart_match.exceptions.CompanyNotFoundException;
import br.com.devictoralmeida.smart_match.modules.company.entities.JobEntity;
import br.com.devictoralmeida.smart_match.modules.company.repositories.CompanyRepository;
import br.com.devictoralmeida.smart_match.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {
  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private CompanyRepository companyRepository;

  public JobEntity execute(JobEntity jobEntity) {
    this.companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
      throw new CompanyNotFoundException();
    });

    return this.jobRepository.save(jobEntity);
  }
}
