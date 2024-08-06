package br.com.devictoralmeida.smart_match.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {
    @Schema(example = "John Doe")
    private String name;

    @Schema(example = "Java Developer")
    private String description;

    @Schema(example = "johndoe")
    private String username;

    @Schema(example = "mail@example.com")
    private String email;

    private UUID id;
}
