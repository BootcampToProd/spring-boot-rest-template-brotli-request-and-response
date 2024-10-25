package com.bootcamptoprod.controller.mock;

import com.aayushatharva.brotli4j.decoder.Decoder;
import com.aayushatharva.brotli4j.encoder.Encoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MockBrotliController {

    @PostMapping(value = "/mock/brotli-request-response")
    public ResponseEntity<byte[]> processCompressedData(@RequestBody byte[] compressedRequestBody, @RequestHeader HttpHeaders headers) throws IOException {
        // Step 1: Decompress the Brotli request body
        byte[] decompressedBody = Decoder.decompress(compressedRequestBody).getDecompressedData();

        // Here, you would typically deserialize the decompressed body into a model (e.g., Employee)
        // For simplicity, we'll just print out the decompressed string data
        String decompressedRequest = new String(decompressedBody);
        System.out.println("Decompressed Request: " + decompressedRequest);


        // Step 2: Compress the response body using Brotli
        byte[] compressedResponseBody = Encoder.compress(decompressedRequest.getBytes());

        // Step 3: Return Brotli-compressed response
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Encoding", "br");  // Set Brotli encoding

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(compressedResponseBody);
    }
}
