package br.com.devictoralmeida.smart_match.modules.candidate.repository;

import br.com.devictoralmeida.smart_match.modules.candidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
    
}
