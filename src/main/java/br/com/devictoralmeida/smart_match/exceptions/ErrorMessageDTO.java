package br.com.devictoralmeida.smart_match.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // Aqui cria um construtor com todos os argumentos, inicializando o objeto já preenchido.
public class ErrorMessageDTO {

    // Das mensagens de erro eu quero apenas o campo e a mensagem.
    private String message;
    private String field;
}
