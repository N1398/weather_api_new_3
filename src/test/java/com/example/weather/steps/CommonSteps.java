package com.example.weather.steps;

import com.example.weather.stub.WireMockConfig;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class CommonSteps {
    @BeforeStories
    public void setUp() {
        System.out.println("Starting WireMock server");
        WireMockConfig.start();
    }

    @AfterStories
    public void tearDown() {
        System.out.println("Stopping WireMock server");
        WireMockConfig.stop();
    }

    public static void compareValues(String field, String expected, String actual, Map<String, String> differences) {
        if (!Objects.equals(expected, actual)) {
            differences.put(field, String.format("expected '%s' but was '%s'", expected, actual));
        }
    }

    static String formatDouble(double value) {
        return String.format(Locale.US, "%.1f", value);
    }

}