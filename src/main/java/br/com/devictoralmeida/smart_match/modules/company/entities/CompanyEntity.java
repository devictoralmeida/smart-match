package br.com.devictoralmeida.smart_match.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "companies")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "This field is required")
    private String name;

    @NotBlank(message = "This field is required")
    @Pattern(regexp = "\\S+", message = "The [username] field cannot contain blanks")
    private String username;

    @Email(message = "The [email] field must has a valid email address")
    private String email;

    @Length(min = 6, max = 100, message = "The [password] field must contain 6 to 100 characters")
    private String password;

    private String website;

    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
