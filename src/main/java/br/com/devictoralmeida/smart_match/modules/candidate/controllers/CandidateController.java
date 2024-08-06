package br.com.devictoralmeida.smart_match.modules.candidate.controllers;

import br.com.devictoralmeida.smart_match.modules.candidate.CandidateEntity;
import br.com.devictoralmeida.smart_match.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.devictoralmeida.smart_match.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.devictoralmeida.smart_match.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.devictoralmeida.smart_match.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.devictoralmeida.smart_match.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.devictoralmeida.smart_match.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/candidates")
@Tag(name = "Candidato", description = "Informações o candidato")
public class CandidateController {
    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping()
    @Operation(summary = "Cadastro de candidato", description = "Essa rota é responsável por cadastrar um novo candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "User already exists")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateData) {
        try {
            var result = this.createCandidateUseCase.execute(candidateData);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')") // Aqui eu digo que apenas candidates podem acessar essa rota
    @Operation(summary = "Acessar dados do usuário", description = "Essa rota é responsável por trazer os dados do usuário através do token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getProfile(HttpServletRequest request) {
        String candidateId = request.getAttribute("candidate_id").toString();
        UUID convertedCandidateId = UUID.fromString(candidateId);

        try {
            var candidate = this.profileCandidateUseCase.execute(convertedCandidateId);
            return ResponseEntity.ok().body(candidate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Essa rota é responsável por listar todas as vagas disponíveis baseadas no filtro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })

    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCase.execute(filter);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Inscrição do candidato para uma vaga", description = "Essa rota é responsável por realizar a inscrição do candidato em uma vaga")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {
        String candidateId = request.getAttribute("candidate_id").toString();
        UUID convertedCandidateId = UUID.fromString(candidateId);

        try {
            var result = this.applyJobCandidateUseCase.execute(convertedCandidateId, idJob);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
