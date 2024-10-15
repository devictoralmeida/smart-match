package br.com.devictoralmeida.smart_match.modules.company.useCases;

import br.com.devictoralmeida.smart_match.exceptions.UserFoundException;
import br.com.devictoralmeida.smart_match.modules.company.entities.CompanyEntity;
import br.com.devictoralmeida.smart_match.modules.company.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCompanyUseCase {
  private final CompanyRepository companyRepository;
  private final PasswordEncoder passwordEncoder;

  public CompanyEntity execute(CompanyEntity companyEntity) {
    companyRepository
      .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
      .ifPresent((user) -> {
        throw new UserFoundException();
      });

    var password = passwordEncoder.encode(companyEntity.getPassword());
    companyEntity.setPassword(password);
    return companyRepository.save(companyEntity);
  }
}
