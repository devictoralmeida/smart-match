package br.com.devictoralmeida.smart_match.modules.company.repositories;

import br.com.devictoralmeida.smart_match.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, UUID> {
  List<JobEntity> findByDescriptionContainingIgnoreCase(String filter);
}
