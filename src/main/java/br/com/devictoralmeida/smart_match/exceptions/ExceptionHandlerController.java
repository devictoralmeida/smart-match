package br.com.devictoralmeida.smart_match.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Precisa dessa anotation para tratar a exceção antes de retorna-la ao cliente.
@ControllerAdvice
public class ExceptionHandlerController {

    private MessageSource messageSource;

    // Construtor
    public ExceptionHandlerController(MessageSource message) {
        this.messageSource = message;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        // Vamos criar um array para receber todos os erros que podem retornar
        List<ErrorMessageDTO> dto = new ArrayList<ErrorMessageDTO>();

        // Vamos percorrer toda o erray de erros e criar um objeto ErrorMessageDTO recebendo a mensagem e o campo do erro, depois vamos joga-lo para o array acima

        exception.getBindingResult().getFieldErrors().forEach(err -> {
            // Aqui estamos tratando a mensagem
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());

            ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());
            dto.add(error);
        });

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
