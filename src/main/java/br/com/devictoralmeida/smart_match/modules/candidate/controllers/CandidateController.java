package br.com.devictoralmeida.smart_match.modules.candidate.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devictoralmeida.smart_match.modules.candidate.CandidateEntity;

import br.com.devictoralmeida.smart_match.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.devictoralmeida.smart_match.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/candidates")
public class CandidateController {
    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @PostMapping()
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
    public ResponseEntity<Object> getProfile(HttpServletRequest request) {
        var candidateId = request.getAttribute("candidate_id");

        try {
            var candidate = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
            return ResponseEntity.ok().body(candidate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
