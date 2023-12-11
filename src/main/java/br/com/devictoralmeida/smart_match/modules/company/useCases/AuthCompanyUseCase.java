package br.com.devictoralmeida.smart_match.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.devictoralmeida.smart_match.exceptions.InvalidCredentialsException;
import br.com.devictoralmeida.smart_match.modules.company.dto.AuthCompanyDTO;
import br.com.devictoralmeida.smart_match.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Precisamos pegar o valor da Secret Key presente no applicaiton.properties
    @Value("${security.token.secret}")
    private String secretKey;

    public String execute(AuthCompanyDTO authCompanyDTO) throws InvalidCredentialsException {
        // Verificar se existe a empresa pelo username
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("Invalid credentials");
                });

        // Verificar se as senhas são iguais
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        // Se for diferente --> Erro
        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        // Se der ok --> Gerar token
        // Vamos dizer qual algoritmo vamos usar, o JWT é o HMAC256, nos () vem a secret_key
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var token = JWT.create().withIssuer("Smart Match") // No withIssuer você coloca o emissor do token
            .withSubject(company.getId().toString()) // No withSubject é o subject, ele espera uma String invés de um UUID.)
            .sign(algorithm);
        return token;
    }
}
