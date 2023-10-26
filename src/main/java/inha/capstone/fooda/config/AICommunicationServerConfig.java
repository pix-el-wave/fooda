package inha.capstone.fooda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AICommunicationServerConfig {

    @Value("${ai.python.url}")
    private String AICommunicationServerUrl;

    @Bean
    public WebClient aiCommunicationServerWebClient() {
        return WebClient.builder()
                .baseUrl(AICommunicationServerUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
