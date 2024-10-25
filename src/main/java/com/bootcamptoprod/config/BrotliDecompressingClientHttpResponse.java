package com.bootcamptoprod.config;

import com.aayushatharva.brotli4j.decoder.Decoder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BrotliDecompressingClientHttpResponse implements ClientHttpResponse {
    private final ClientHttpResponse response;
    private byte[] decompressedBody;

    public BrotliDecompressingClientHttpResponse(ClientHttpResponse response) throws IOException {
        this.response = response;

        // Check if the response has 'Content-Encoding: br' header
        if (isBrotliEncoded(response)) {
            // Decompress the Brotli response body
            this.decompressedBody = Decoder.decompress(response.getBody().readAllBytes()).getDecompressedData();
        } else {
            // If not Brotli encoded, just pass through the original response body
            this.decompressedBody = response.getBody().readAllBytes();
        }
    }

    @Override
    public InputStream getBody() {
        return new ByteArrayInputStream(decompressedBody);
    }

    @Override
    public org.springframework.http.HttpHeaders getHeaders() {
        return response.getHeaders();
    }

    @Override
    public HttpStatusCode getStatusCode() throws IOException {
        return response.getStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return response.getStatusText();
    }

    @Override
    public void close() {
        response.close();
    }

    private boolean isBrotliEncoded(ClientHttpResponse response) {
        return response.getHeaders().getFirst("Content-Encoding") != null &&
                response.getHeaders().getFirst("Content-Encoding").equalsIgnoreCase("br");
    }
}
