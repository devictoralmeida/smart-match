package br.com.devictoralmeida.smart_match.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageDTO {


  private String message;
  private String field;
}
