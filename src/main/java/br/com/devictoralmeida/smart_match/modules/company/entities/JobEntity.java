package br.com.devictoralmeida.smart_match.modules.company.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "jobs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "This field is required")
  @Schema(example = "Vaga para pessoa desenvolvedora júnior")
  private String description;

  @Schema(example = "GYMPass, plano de saúde, dayoff no dia do aniversário")
  private String benefits;

  @NotBlank(message = "This field is required")
  @Schema(example = "Junior")
  private String level;

  @ManyToOne()
  @JoinColumn(name = "company_id", insertable = false, updatable = false)
  private CompanyEntity companyEntity;

  @Column(name = "company_id", nullable = false)
  private UUID companyId;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
