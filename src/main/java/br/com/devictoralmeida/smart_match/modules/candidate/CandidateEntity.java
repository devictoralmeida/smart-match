package br.com.devictoralmeida.smart_match.modules.candidate;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

// Esse decorator do lombok cria automaticamente os getters e setters para todas as propriedades dessa entidade
@Data
public class CandidateEntity {
    private UUID id;
    
    @NotBlank()
    private String name;

    // Aqui não vamos permitir que o username contenha espaços
    @NotBlank()
    @Pattern(regexp = "\\S+", message = "The [username] field cannot contain blanks")
    private String username;

    @Email(message = "The [email] field must has a valid email address")
    private String email;

    @Length(min = 6, max = 100, message = "The [password] field must contain 6 to 100 characters")
    private String password;

    @NotBlank()
    private String description;
    private String resume;
}
