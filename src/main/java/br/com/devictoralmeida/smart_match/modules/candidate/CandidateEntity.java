package br.com.devictoralmeida.smart_match.modules.candidate;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

// Esse decorator do lombok cria automaticamente os getters e setters para todas as propriedades dessa entidade
@Data
public class CandidateEntity {

    private UUID id;

    private String name;

    // Aqui não vamos permitir que o username contenha espaços
    @Pattern(regexp = "^(?!\\s*$).+", message = "The [username] field cannot contain blanks")
    private String username;

    @Email(message = "The [email] field must has a valid email address")
    private String email;

    @Length(min = 6, max=120)
    private String password;

    private String description;

    private String resume;
}
