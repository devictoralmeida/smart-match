package br.com.devictoralmeida.smart_match.modules.company.controllers;

import br.com.devictoralmeida.smart_match.modules.company.dto.CreateJobDTO;
import br.com.devictoralmeida.smart_match.modules.company.entities.CompanyEntity;
import br.com.devictoralmeida.smart_match.modules.company.repositories.CompanyRepository;
import br.com.devictoralmeida.smart_match.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // Criando um ambiente web com porta randômica
@ActiveProfiles("test") // Pegar o application-test.properties
class CreateJobControllerTest {

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private CompanyRepository companyRepository;

  @BeforeEach
  void setup() {
    // Criando estrutura para rodar os testes
    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  @DisplayName("Should be able to create a new job")
  void should_be_able_to_create_a_new_job() throws Exception {
    // 1º Vamos precisar criar uma empresa
    var company = CompanyEntity.builder()
        .description("Description Test")
        .email("test@mail.com")
        .password("123456")
        .username("java_test")
        .name("Test Company")
        .build();

    // Ele usou o Save com o Flush para salvar imediatamente, invés de aguardar toda
    // a requisição, isso pq nós vamos precisar do id dessa company logo abaixo
    company = this.companyRepository.saveAndFlush(company);

    // Criando o corpo da requisição
    CreateJobDTO createJobDTO = CreateJobDTO.builder()
        .benefits("Benefits Test")
        .description("Description Test")
        .level("Junior")
        .build();

    // Transformando o corpo da requisição em JSON
    String requestBody = TestUtils.objectToJson(createJobDTO);

    // Fazendo a requisição
    mvc.perform(MockMvcRequestBuilders.post("/companies/jobs")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody)
        // Inserindo o token na requisição
        .header("Authorization",
            TestUtils.generateToken(company.getId(),
                "651651askdbajfgbaksdbangfadnmak")))
        .andExpect(MockMvcResultMatchers.status().isOk());

  }

  @Test
  @DisplayName("Should NOT be able to create a new job without a company")
  void should_not_be_able_to_create_a_new_job_without_a_company() throws Exception {
    var randomUUID = UUID.randomUUID();

    CreateJobDTO createJobDTO = CreateJobDTO.builder()
        .benefits("Benefits Test")
        .description("Description Test")
        .level("Junior")
        .build();

    // Transformando o corpo da requisição em JSON
    String requestBody = TestUtils.objectToJson(createJobDTO);

    mvc.perform(MockMvcRequestBuilders.post("/companies/jobs")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody)
        // Inserindo o token na requisição
        .header("Authorization",
            TestUtils.generateToken(randomUUID,
                "651651askdbajfgbaksdbangfadnmak")))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

  }
}
