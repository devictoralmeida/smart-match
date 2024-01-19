package br.com.devictoralmeida.smart_match.exceptions;

public class CompanyNotFoundException extends RuntimeException {
  public CompanyNotFoundException() {
      super("Company not found");
  }
}
