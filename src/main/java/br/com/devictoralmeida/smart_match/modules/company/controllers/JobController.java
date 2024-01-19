package br.com.devictoralmeida.smart_match.modules.company.controllers;

import br.com.devictoralmeida.smart_match.modules.company.dto.CreateJobDTO;
import br.com.devictoralmeida.smart_match.modules.company.entities.JobEntity;
import br.com.devictoralmeida.smart_match.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController()
@RequestMapping("/companies/jobs")
public class JobController {
  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping()
  @PreAuthorize("hasRole('COMPANY')") // Somente role de company vai acessar essa rota
  @Tag(name = "Vagas", description = "Informações das vagas")
  @Operation(summary = "Cadastro de vagas", description = "Essa rota é responsável por cadastrar as vagas dentro da empresa")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = JobEntity.class))
      })
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
    // Vamos pegar o company_id criado pelo filter, ele retorna um obj
    var companyId = request.getAttribute("company_id");

    // Precisamos transformar o obj acima em string e depois em UUID e seta-lo no
    // nosso jobEntity
    // Obj --> String --> UUID

    try {
      var jobEntity = JobEntity.builder()
          .companyId(UUID.fromString(companyId.toString()))
          .benefits(createJobDTO.getBenefits())
          .description(createJobDTO.getDescription())
          .level(createJobDTO.getLevel())
          .build();

      var result = this.createJobUseCase.execute(jobEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }
}
