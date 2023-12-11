package br.com.devictoralmeida.smart_match.modules.candidate;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<1, 2> ; 1 é a entidade e o 2 é o tipo da chave primária

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
    // Esse optional nos permite ter mais alguns acessos no retorno

    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
}
