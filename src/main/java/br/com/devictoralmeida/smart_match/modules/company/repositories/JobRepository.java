package br.com.devictoralmeida.smart_match.modules.company.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devictoralmeida.smart_match.modules.company.entities.JobEntity;



public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    
}
