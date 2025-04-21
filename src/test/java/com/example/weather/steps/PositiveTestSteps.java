package com.example.weather.steps;

import com.example.weather.api.WeatherApiEndpoints;
import com.example.weather.dto.BulkWeatherResponse;
import com.example.weather.stub.WireMockConfig;
import com.example.weather.api.WeatherApiClient;
import com.example.weather.dto.CurrentWeatherResponse;
import com.example.weather.stub.WeatherApiStub;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.weather.steps.CommonSteps.formatDouble;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static org.assertj.core.api.Assertions.assertThat;

public class PositiveTestSteps {
    private static final Logger logger = LogManager.getLogger(PositiveTestSteps.class);

    private WeatherApiClient apiClient;
    private Response response;
    private CurrentWeatherResponse weatherResponse;
    private BulkWeatherResponse bulkResponse;


    @Given("a weather API client with base URL '$baseUrl' and API key '$apiKey'")
    public void setupApiClient(String baseUrl, String apiKey) {
        String resolvedUrl = baseUrl.replace("${wiremock.port}", String.valueOf(WireMockConfig.getPort()));
        apiClient = new WeatherApiClient(resolvedUrl, apiKey);
    }
    @Given("bulk-request a weather API client with base URL '$baseUrl' and API key '$apiKey'")
    public void setupBulkApiClient(String baseUrl, String apiKey) {
        String resolvedUrl = baseUrl.replace("${wiremock.port}", String.valueOf(WireMockConfig.getPort()));
        apiClient = new WeatherApiClient(resolvedUrl, apiKey);
    }


    @Given("WireMock is configured to return current weather for '$city' from file '$responseFile'")
    public void setupPositiveWeatherStub(String city, String responseFile) {
        if (apiClient == null) {
            throw new IllegalStateException("API client is not initialized");
        }
        WeatherApiStub stub = new WeatherApiStub("localhost", WireMockConfig.getPort());
        stub.stubCurrentWeather(city, responseFile, apiClient.getApiKey());
    }

    @When("I request current weather for '$city'")
    public void requestCurrentWeather(String city) {
        if (apiClient == null) {
            logger.error("API client is not initialized before making GET request");
            throw new IllegalStateException("API client is not initialized");
        }
        response = given()
                .spec(apiClient.getSpec())
                .queryParam("q", city)
                .when()
                .get(WeatherApiEndpoints.CURRENT_WEATHER);
        weatherResponse = response.as(CurrentWeatherResponse.class);
    }


    @Then("the response status code should be $statusCode")
    public void verifyStatusCode(int statusCode) {
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
    }


    @Then("the weather data should match expected values:$table")
    public void verifyWeatherData(ExamplesTable table) {
        Map<String, String> expectedValues = new HashMap<>();
        for (Map<String, String> row : table.getRows()) {
            expectedValues.put(row.get("key"), row.get("value"));
        }
        logger.info("Verifying weather data against expected values: {}", expectedValues);
        assertThat(weatherResponse.getLocation().getName())
                .isEqualTo(expectedValues.get("location.name"));
        assertThat(weatherResponse.getLocation().getRegion())
                .isEqualTo(expectedValues.get("location.region"));
        assertThat(weatherResponse.getLocation().getCountry())
                .isEqualTo(expectedValues.get("location.country"));
        assertThat(formatDouble(weatherResponse.getCurrent().getTempC()))
                .isEqualTo(expectedValues.get("current.temp_c"));
        assertThat(weatherResponse.getCurrent().getCondition().getText())
                .isEqualTo(expectedValues.get("current.condition.text"));
    }

    @Given("WireMock is configured to return bulk weather response for request from file '$requestFile' with response from file '$responseFile'")
    public void setupBulkStub(String requestFile, String responseFile) throws IOException {
        WeatherApiStub stub = new WeatherApiStub("localhost", WireMockConfig.getPort());
        stub.stubBulkWeather("bulk", requestFile, responseFile, apiClient.getApiKey());
    }

    @When("I send a bulk weather POST request with body from file '$requestFile'")
    public void sendBulkWeatherRequest(String requestFile) throws IOException {
        String body = WeatherApiStub.readFile("__files/" + requestFile);
        response = given()
                .spec(apiClient.getSpec())
                .body(body)
                .post(WeatherApiEndpoints.CURRENT_WEATHER);
        bulkResponse = response.as(BulkWeatherResponse.class);
    }

    @Then("the response should contain $count weather entries")
    public void verifyBulkEntryCount(int count) {
        assertThat(bulkResponse.getBulk()).hasSize(count);
    }

    @Then("weather entry $index should match expected values:$table")
    public void verifyBulkEntryValues(int index, ExamplesTable table) {
        logger.info("Verifying bulk entry at index {}", index);
        BulkWeatherResponse.BulkItem item = bulkResponse.getBulk().get(index);
        Map<String, String> expected = new HashMap<>();
        table.getRows().forEach(row -> expected.put(row.get("key"), row.get("value")));

        BulkWeatherResponse.Query q = item.getQuery();

        expected.forEach((key, value) -> {
            switch (key) {
                case "query.q" -> assertThat(q.getQ()).isEqualTo(value);
                case "query.location.name" -> assertThat(q.getLocation().getName()).isEqualTo(value);
                case "query.location.country" -> assertThat(q.getLocation().getCountry()).isEqualTo(value);
                case "query.current.temp_c" -> assertThat(formatDouble(q.getCurrent().getTempC())).isEqualTo(value);
                case "query.current.condition.text" -> assertThat(q.getCurrent().getCondition().getText()).isEqualTo(value);
                default -> throw new IllegalArgumentException("Unexpected key: " + key);
            }
        });
    }

}