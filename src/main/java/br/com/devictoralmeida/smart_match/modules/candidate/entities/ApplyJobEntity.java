package br.com.devictoralmeida.smart_match.modules.candidate.entities;

import br.com.devictoralmeida.smart_match.modules.company.entities.JobEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "apply_jobs")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJobEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
  private CandidateEntity candidateEntity;

  @Column(name = "candidate_id")
  private UUID candidateId;

  @ManyToOne
  @JoinColumn(name = "job_id", insertable = false, updatable = false)
  private JobEntity jobEntity;

  @Column(name = "job_id")
  private UUID jobId;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
