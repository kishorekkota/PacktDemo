package com.example.consumerapp;


import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.eclipse.jetty.server.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "APIProvider", port = "1234")
public class ProviderContractTest {



    @Pact(consumer = "APIConsumer")
    public RequestResponsePact validAccountFromProvider(PactDslWithProvider builder) throws Exception {


        return builder
                .given("account is existing")
                .uponReceiving("a request for an account")
                .path("/account/1001")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(LambdaDsl.newJsonBody((o) -> {
                    o.numberType("accountId", 1001);
                    o.stringType("name", "John Doe");
                    o.stringType("email", "test@test.com");
                    o.booleanType("active", true);
                    o.stringType("opendate", "2020-01-01");

                }).build())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "validAccountFromProvider")
    public void testValidAccountFromProvider(MockServer mockServer) throws Exception {
           // Arrange
            String url = mockServer.getUrl() + "/account/1001";
            // Act
            ResponseEntity<Account> response = new RestTemplate().getForEntity(url, Account.class);

            Account accountExpected = new Account();



            accountExpected.setAccountId("1001");
            accountExpected.setName("John Doe");
            accountExpected.setEmail("test@test.com");
            accountExpected.setActive(true);
            accountExpected.setOpendate("2020-01-01");

            // Assert
            assertEquals(accountExpected, response.getBody());

    }

}