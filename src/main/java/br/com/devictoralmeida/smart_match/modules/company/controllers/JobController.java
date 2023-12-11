package br.com.devictoralmeida.smart_match.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devictoralmeida.smart_match.modules.company.entities.JobEntity;
import br.com.devictoralmeida.smart_match.modules.company.useCases.CreateJobUseCase;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping()
    public JobEntity create(@Valid @RequestBody JobEntity jobData) {
        return this.createJobUseCase.execute(jobData);
    }
}
