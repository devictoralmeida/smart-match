package br.com.devictoralmeida.smart_match.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException() {
        super("User already exists");
    }
}
