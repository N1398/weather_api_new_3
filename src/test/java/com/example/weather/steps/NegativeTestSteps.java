package com.example.weather.steps;

import com.example.weather.api.WeatherApiEndpoints;
import com.example.weather.stub.WireMockConfig;
import com.example.weather.api.WeatherApiClient;
import com.example.weather.dto.ErrorResponse;
import com.example.weather.stub.WeatherApiStub;
import io.restassured.response.Response;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NegativeTestSteps {
    private static final Logger logger = LogManager.getLogger(NegativeTestSteps.class);

    private Response response;
    private ErrorResponse errorResponse;
    private WeatherApiClient apiClient;
    private String apiKey;

    @BeforeScenario
    public void resetState() {
        apiClient = null;
        apiKey = null;
        response = null;
        errorResponse = null;
    }

    @Given("a negative weather API client with base URL '$baseUrl' and API key '$apiKey'")
    public void setupNegativeApiClient(String baseUrl, String apiKey) {
        this.apiKey = apiKey;
        String resolvedUrl = baseUrl.replace("${wiremock.port}", String.valueOf(WireMockConfig.getPort()));
        try {
            apiClient = new WeatherApiClient(resolvedUrl, apiKey);
        } catch (Exception e) {
            logger.error("Failed to initialize API client: {}", e.getMessage(), e);
            throw new RuntimeException("API client initialization failed", e);
        }
    }

//
//    @Given("a weather API client with base URL '$baseUrl' and API key '$apiKey'")
//    public void setupApiClient(String baseUrl, String apiKey) {
//        this.apiKey = apiKey;
//        String resolvedUrl = baseUrl.replace("${wiremock.port}", String.valueOf(WireMockConfig.getPort()));
//        try {
//            apiClient = new WeatherApiClient(resolvedUrl, apiKey);
//        } catch (Exception e) {
//            throw new RuntimeException("API client initialization failed", e);
//        }
//    }
//    @Given("a weather API client with base URL '$baseUrl' and API key '$apiKey'")
//    public void setupApiClientWithoutQ(String baseUrl, String apiKey) {
//        this.apiKey = apiKey;
//        String resolvedUrl = baseUrl.replace("${wiremock.port}", String.valueOf(WireMockConfig.getPort()));
//        try {
//            apiClient = new WeatherApiClient(resolvedUrl, apiKey);
//        } catch (Exception e) {
//            throw new RuntimeException("API client initialization failed", e);
//        }
//    }


    @Given("WireMock is configured to return error with status $statusCode, code $errorCode and message $errorMessage")
    public void setupErrorStub(int statusCode, int errorCode, String errorMessage) {
        if (apiKey == null) {
            logger.warn("apiKey is null during WireMock setup, using default-key");
        }
        WeatherApiStub stub = new WeatherApiStub("localhost", WireMockConfig.getPort());
        stub.stubErrorResponse(statusCode, errorCode, errorMessage, apiKey != null ? apiKey : "default-key");
    }

    @When("I make an invalid request without query parameter")
    public void makeInvalidRequestWithoutQueryParam() {
        if (apiClient == null) {
            logger.error("API client is not initialized before making request. Current state: apiKey={}", apiKey);
            throw new IllegalStateException("API client is not initialized");
        }
        response = given()
                .spec(apiClient.getSpec())
                .when()
                .get(WeatherApiEndpoints.CURRENT_WEATHER);
        errorResponse = response.as(ErrorResponse.class);
    }

    @Then("the error response should have status $statusCode, code $errorCode and message $errorMessage")
    public void verifyErrorResponse(int statusCode, int errorCode, String errorMessage) {
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
        assertThat(errorResponse.getError().getCode()).isEqualTo(errorCode);
        assertThat(errorResponse.getError().getMessage()).isEqualTo(errorMessage);
        logger.info("Error response verification completed successfully");
    }
}