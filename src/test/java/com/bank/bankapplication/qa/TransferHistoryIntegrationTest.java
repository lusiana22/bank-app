package com.bank.bankapplication.qa;

import com.bank.bankapplication.CoinmeCodingChallengeOowfpzApplication;
import com.bank.bankapplication.data.TransferHistoryResponse;
import com.bank.bankapplication.persistence.entity.TransferHistory;
import com.bank.bankapplication.persistence.repository.TransferHistoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoinmeCodingChallengeOowfpzApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferHistoryIntegrationTest {
    private static final String FROM_ACCOUNT_NUMBER = "1234567876";
    private static final String TO_ACCOUNT_NUMBER = "6763738723";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(200);
    private static final String GET_TRANSFER_HISTORY_URI = "/api/transfer-history/";

    private HttpHeaders headers = new HttpHeaders();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TransferHistoryRepository transferHistoryRepository;

    @Before
    public void setUp() {
        transferHistoryRepository.deleteAll();
    }

    @Test
    public void getTransferHistory() {
        //save the transfer history in DB
        TransferHistory transferHistory = TransferHistory.builder()
                .fromAccountNumber(FROM_ACCOUNT_NUMBER)
                .toAccountNumber(TO_ACCOUNT_NUMBER)
                .amount(AMOUNT)
                .build();
        transferHistoryRepository.save(transferHistory);

        //make the call
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<List<TransferHistoryResponse>> response = testRestTemplate.exchange(
                formFullURLWithPort(GET_TRANSFER_HISTORY_URI + FROM_ACCOUNT_NUMBER + "/get-transfer-history"),
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<TransferHistoryResponse>>() {});

        //make assertions to make sure that the result is what we expect
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().get(0).getFromAccountNumber()).isEqualTo(FROM_ACCOUNT_NUMBER);
        assertThat(response.getBody().get(0).getToAccountNumber()).isEqualTo(TO_ACCOUNT_NUMBER);
        assertThat(response.getBody().get(0).getAmount()).isEqualTo("200.00");
    }

    private String formFullURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}