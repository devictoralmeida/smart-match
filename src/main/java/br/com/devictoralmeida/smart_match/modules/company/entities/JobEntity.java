package br.com.devictoralmeida.smart_match.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "jobs")
@Builder // Aqui
@AllArgsConstructor // Preciso devido ao Builder
@NoArgsConstructor // Preciso devido ao Builder
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
    // O insertable e o updatable indica ao ORM que essa coluna não sofrerá modificações, oq será manipulado é o company id
    private CompanyEntity companyEntity;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
