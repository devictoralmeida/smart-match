package br.com.devictoralmeida.smart_match.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devictoralmeida.smart_match.modules.candidate.CandidateEntity;

import br.com.devictoralmeida.smart_match.modules.candidate.useCases.CreateCandidateUseCase;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/candidates")
public class CandidateController {
    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateData) {
        try {
            var result = this.createCandidateUseCase.execute(candidateData);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
