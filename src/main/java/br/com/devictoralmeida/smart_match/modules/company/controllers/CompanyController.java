package br.com.devictoralmeida.smart_match.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devictoralmeida.smart_match.modules.company.entities.CompanyEntity;
import br.com.devictoralmeida.smart_match.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyData) {
        try {
            var result = this.createCompanyUseCase.execute(companyData);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
