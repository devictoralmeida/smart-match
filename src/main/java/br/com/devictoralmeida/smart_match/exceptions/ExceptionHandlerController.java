package br.com.devictoralmeida.smart_match.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

  private MessageSource messageSource;


  public ExceptionHandlerController(MessageSource message) {
    messageSource = message;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {


    List<ErrorMessageDTO> dto = new ArrayList<ErrorMessageDTO>();


    exception.getBindingResult().getFieldErrors().forEach(err -> {

      String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());

      ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());
      dto.add(error);
    });

    return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
  }
}
