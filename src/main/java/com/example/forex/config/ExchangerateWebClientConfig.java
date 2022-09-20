package com.example.forex.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ExchangerateWebClientConfig {

    @Value("${webclient.url}")
    private String exchangerateWebClientUrl;

    @Bean
    public WebClient exchangerateWebClient() {
        return WebClient.builder()
                .baseUrl(exchangerateWebClientUrl)
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
