package idatt2106.systemutvikling.sparesti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idatt2106.systemutvikling.sparesti.dto.TransactionDTO;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class TransactionControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void getLatestExpenses_callsCorrectService() throws Exception {
        Long transactionId = 4729387L;
        Long accountNr = 79L;
        String transactionTitle = "Test transaction";
        Date time = null;
        Long debtorAccount = accountNr;
        String debtorName = "Debtor";
        Long creditorAccount = 78L;
        String creditorName = "Creditor";
        Long amount = 100L;
        String currency = "NOK";

        Transaction transaction = new Transaction(
                transactionId,
                accountNr,
                transactionTitle,
                time,
                debtorAccount,
                debtorName,
                creditorAccount,
                creditorName,
                amount,
                currency
        );

        TransactionDTO transactionDTO = new TransactionDTO(
                transactionId,
                transactionTitle,
                time,
                debtorAccount,
                debtorName,
                creditorAccount,
                creditorName,
                amount,
                currency,
                transaction.getCategory()
        );

        List<Transaction> transactions = List.of(transaction);
        List<TransactionDTO> transactionDTOs = List.of(transactionDTO);

        given(transactionService.getLatestExpensesForCurrentUser_CheckingAccount_Categorized()).willReturn(transactions);

        mvc.perform(get("/user/transaction/30-day-expenses"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(transactionDTOs)));
    }



    @Test
    public void getLatestExpenses_returnsCorrectStatusWhenServiceReturnsNull() throws Exception {
        given(transactionService.getLatestExpensesForCurrentUser_CheckingAccount_Categorized()).willReturn(null);

        mvc.perform(get("/user/transaction/30-day-expenses"))
                .andExpect(status().is4xxClientError());
    }



    @Test
    public void getLatestExpenses_returnsCorrectStatusWhenNoExpensesWereFound() throws Exception {
        given(transactionService.getLatestExpensesForCurrentUser_CheckingAccount_Categorized()).willReturn(new ArrayList<>());

        mvc.perform(get("/user/transaction/30-day-expenses"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(status().isNoContent());
    }
}
