package br.com.devictoralmeida.smart_match.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devictoralmeida.smart_match.modules.company.entities.JobEntity;
import br.com.devictoralmeida.smart_match.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {
    @Autowired
    private JobRepository jobRepository;

    public JobEntity execute(JobEntity jobEntity) {
        return this.jobRepository.save(jobEntity);
    }
}
