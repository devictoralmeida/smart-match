package br.com.devictoralmeida.smart_match.modules.candidate.repository;

import br.com.devictoralmeida.smart_match.modules.candidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {

}
