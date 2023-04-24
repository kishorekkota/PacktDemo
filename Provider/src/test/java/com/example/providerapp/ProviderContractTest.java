package com.example.providerapp;

import au.com.dius.pact.provider.junit.RestPactRunner;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junitsupport.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.MockMvcTarget;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import org.junit.Before;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.ConfigurableWebApplicationContext;

@RunWith(SpringRestPactRunner.class) // Custom pact runner, child of PactRunner which runs only REST tests
@Provider("APIProvider") // Set up name of tested provider
@PactFolder("src/main/resources/pacts/")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderContractTest {

    @InjectMocks
    private AccountController controller = new AccountController();

    private static ConfigurableWebApplicationContext application;

    @TestTarget
    public final MockMvcTarget target = new MockMvcTarget();

    @Before //Method will be run before each test of interaction
    public void before() {
        //initialize your mocks using your mocking framework
        MockitoAnnotations.initMocks(this);

        //configure the MockMvcTarget with your controller and controller advice
        target.setControllers(controller);

       // application = (ConfigurableWebApplicationContext) SpringApplication.run(ProviderAppApplication.class);

    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)

    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("account is existing")
    public void createAccount() {
        // do nothing
    }

}