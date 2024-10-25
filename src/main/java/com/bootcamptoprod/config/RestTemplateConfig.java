package com.bootcamptoprod.config;

import com.aayushatharva.brotli4j.encoder.Encoder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.getInterceptors().add(brotliRequestInterceptor());
        restTemplate.getInterceptors().add(brotliResponseInterceptor());
        return restTemplate;
    }

    private ClientHttpRequestInterceptor brotliRequestInterceptor() {
        return (request, body, execution) -> {
            request.getHeaders().remove("Content-Length");
            // Compress the request body using Brotli
            byte[] compressedBody = Encoder.compress(body);

            // Set the Content-Encoding header to 'br'
            request.getHeaders().set("Content-Encoding", "br");

            // Proceed with the compressed request
            return execution.execute(request, compressedBody);
        };
    }

    public ClientHttpRequestInterceptor brotliResponseInterceptor() {
        return (request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request, body);
            return new BrotliDecompressingClientHttpResponse(response);
        };
    }
}
