package com.example.weather.api;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class WeatherApiClient {
    private static final Logger logger = LogManager.getLogger(WeatherApiClient.class);
    private final String baseUrl;
    private final String apiKey;

    public WeatherApiClient(String baseUrl, String apiKey) {

        if (baseUrl == null || baseUrl.isEmpty()) {
            logger.error("baseUrl is null or empty");
            throw new IllegalArgumentException("baseUrl cannot be null or empty");
        }
        if (apiKey == null || apiKey.isEmpty()) {
            logger.error("apiKey is null or empty");
            throw new IllegalArgumentException("apiKey cannot be null or empty");
        }
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
     }

    public RequestSpecification getSpec() {
        return given()
                .baseUri(baseUrl)
                .queryParam("key", apiKey);
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {return baseUrl;}

}