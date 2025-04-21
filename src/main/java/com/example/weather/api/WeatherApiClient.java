package com.example.weather.api;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
//import org.slf4j.Logger.LogManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.slf4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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