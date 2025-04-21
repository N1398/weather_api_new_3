package com.example.weather.stub;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WireMockConfig {
    private static final Logger logger = LogManager.getLogger(WireMockConfig.class);
    private static final int PORT = 8080; // Fixed port for consistency
    private static WireMockServer wireMockServer;

    public static int getPort() {
        return PORT;
    }

    public static void start() {
        try {
            wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(PORT));
            wireMockServer.start();
        } catch (Exception e) {
            logger.error("Failed to start WireMock server: {}", e.getMessage(), e);
            throw new RuntimeException("WireMock server startup failed", e);
        }
    }

    public static void stop() {
        try {
            if (wireMockServer != null && wireMockServer.isRunning()) {
                wireMockServer.stop();
                logger.info("WireMock server stopped");
            }
        } catch (Exception e) {
            logger.error("Failed to stop WireMock server: {}", e.getMessage(), e);
            throw new RuntimeException("WireMock server shutdown failed", e);
        }
    }

}