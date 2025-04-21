package com.example.weather.stub;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.example.weather.api.WeatherApiEndpoints;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WeatherApiStub {
    private final WireMock wireMock;

    public WeatherApiStub(String host, int port) {
        this.wireMock = new WireMock(host, port);
    }

    public void stubCurrentWeather(String city, String responseFile, String apiKey) {
        wireMock.register(get(urlPathEqualTo(WeatherApiEndpoints.CURRENT_WEATHER))
                .withQueryParam("q", equalTo(city))
                .withQueryParam("key", equalTo(apiKey))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile(responseFile)));
    }

    public void stubErrorResponse(int statusCode, int errorCode, String errorMessage, String apiKey) {
        String errorJson = String.format("{\"error\":{\"code\":%d,\"message\":\"%s\"}}",
                errorCode, errorMessage);

        wireMock.register(get(urlPathEqualTo(WeatherApiEndpoints.CURRENT_WEATHER))
                .withQueryParam("q", absent())
                .withQueryParam("key", apiKey != null ? equalTo(apiKey) : absent())
                .willReturn(aResponse()
                        .withStatus(statusCode)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorJson)));
    }
    public static String readFile(String filePath) throws IOException {
        ClassLoader classLoader = WeatherApiStub.class.getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(filePath.replace("src/test/resources/", ""))) {
            if (is == null) {
                throw new FileNotFoundException("File not found in resources: " + filePath);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public void stubBulkWeather(String location, String requestFile, String responseFile, String apiKey) throws IOException {
        String requestBody = readFile("__files/" + requestFile);
        String responseBody = readFile("__files/" + responseFile);

        stubFor(post(urlPathEqualTo("/v1/current.json"))
                .withQueryParam("key", equalTo(apiKey))
                .withQueryParam("q", equalTo(location))
                .withRequestBody(equalToJson(requestBody))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

}
