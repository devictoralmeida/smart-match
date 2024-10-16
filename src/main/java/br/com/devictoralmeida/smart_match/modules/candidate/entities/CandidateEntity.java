package br.com.devictoralmeida.smart_match.modules.candidate.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "candidates")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CandidateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "This field is required")
  @Schema(example = "John Doe", requiredMode = RequiredMode.REQUIRED, description = "Nome do candidato")
  private String name;


  @NotBlank(message = "This field is required")
  @Pattern(regexp = "\\S+", message = "The [username] field cannot contain blanks")
  @Schema(example = "johndoe", requiredMode = RequiredMode.REQUIRED, description = "Username do candidato")
  private String username;

  @Email(message = "The [email] field must has a valid email address")
  @Schema(example = "mail@example.com", requiredMode = RequiredMode.REQUIRED, description = "E-mail do candidato")
  private String email;

  @Length(min = 6, max = 100, message = "The [password] field must contain 6 to 100 characters")
  @Schema(example = "123456", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED, description = "Senha do candidato")
  private String password;

  @NotBlank(message = "This field is required")
  @Schema(example = "Java Developer", requiredMode = RequiredMode.REQUIRED, description = "Breve descrição do candidato")
  private String description;

  private String resume;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
