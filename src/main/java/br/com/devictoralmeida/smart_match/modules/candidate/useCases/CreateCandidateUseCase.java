package br.com.devictoralmeida.smart_match.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.devictoralmeida.smart_match.exceptions.UserFoundException;
import br.com.devictoralmeida.smart_match.modules.candidate.CandidateEntity;
import br.com.devictoralmeida.smart_match.modules.candidate.CandidateRepository;

@Service // Preciso da anotation de serviço para o useCase
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> { // esse isPresent é uma funcionalidade do Optional
                    throw new UserFoundException();
                });

        var password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);
        return this.candidateRepository.save(candidateEntity);
    }
}
