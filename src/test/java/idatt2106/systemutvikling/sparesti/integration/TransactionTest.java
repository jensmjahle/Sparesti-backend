package idatt2106.systemutvikling.sparesti.integration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import idatt2106.systemutvikling.sparesti.configuration.OpenAIRestTemplateConfig;
import idatt2106.systemutvikling.sparesti.controller.TransactionController;
import idatt2106.systemutvikling.sparesti.dto.PaginatedRequestDTO;
import idatt2106.systemutvikling.sparesti.mockBank.service.AccountService;
import idatt2106.systemutvikling.sparesti.mockBank.service.BankTransactionService;
import idatt2106.systemutvikling.sparesti.mockBank.service.CustomerService;
import idatt2106.systemutvikling.sparesti.security.SecretsConfig;
import idatt2106.systemutvikling.sparesti.security.SecurityConfig;
import idatt2106.systemutvikling.sparesti.service.OpenAIService;
import idatt2106.systemutvikling.sparesti.service.TransactionCategoryCacheService;
import idatt2106.systemutvikling.sparesti.service.TransactionService;
import idatt2106.systemutvikling.sparesti.service.TransactionServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureDataJpa
@Import({ SecurityConfig.class, TransactionService.class, BankTransactionService.class,
          AccountService.class, CustomerService.class, SecretsConfig.class, OpenAIService.class,
          OpenAIRestTemplateConfig.class, TransactionCategoryCacheService.class })
public class TransactionTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;



    @Test
    public void getLatestExpenses_filtersOutUnauthenticatedConnections() throws Exception {
        PaginatedRequestDTO dto = new PaginatedRequestDTO(0, 5);

        mvc.perform(post("/user/transaction/latest/expense")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
