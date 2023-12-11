package br.com.devictoralmeida.smart_match.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devictoralmeida.smart_match.modules.company.dto.CreateJobDTO;
import br.com.devictoralmeida.smart_match.modules.company.entities.JobEntity;
import br.com.devictoralmeida.smart_match.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/companies/jobs")
public class JobController {
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping()
    @PreAuthorize("hasRole('COMPANY')") // Somente role de company vai acessar essa rota
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        // Vamos pegar o company_id criado pelo filter, ele retorna um obj
        var companyId = request.getAttribute("company_id");

        // Precisamos transformar o obj acima em string e depois em UUID e seta-lo no nosso jobEntity
        // Obj --> String --> UUID
        
        var jobEntity = JobEntity.builder()
                .companyId(UUID.fromString(companyId.toString()))
                .benefits(createJobDTO.getBenefits())
                .description(createJobDTO.getDescription())
                .level(createJobDTO.getLevel())
                .build();

        return this.createJobUseCase.execute(jobEntity);
    }
}
