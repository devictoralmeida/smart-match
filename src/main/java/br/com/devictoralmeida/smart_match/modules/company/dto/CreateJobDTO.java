package br.com.devictoralmeida.smart_match.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobDTO {

    @Schema(example = "Vaga para pessoa desenvolvedora júnior", requiredMode = RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "GYMPass, plano de saúde, dayoff no dia do aniversário", requiredMode = RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "Junior", requiredMode = RequiredMode.REQUIRED)
    private String level;
}
