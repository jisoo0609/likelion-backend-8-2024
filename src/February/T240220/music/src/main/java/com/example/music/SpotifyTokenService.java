package com.example.music;

import com.example.music.dto.AccessTokenDTo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

// CleintId와 Secret을 보냄
@Component
public class SpotifyTokenService {
    @Value("b1f1ec003a844785a9ea9a8cfcf414c6")
    private String clientId;
    @Value("${spotify.client-secret}")
    private String clientSecret;
    private final RestClient authRestClient;

    public SpotifyTokenService(RestClient authRestClient) {
        this.authRestClient = authRestClient;
    }

    public AccessTokenDTo getAccessToken() {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("grant_type","client_credentials");
        parts.add("client_id", clientId);
        parts.add("client_secret", clientSecret);

        return authRestClient.post()
                // Content-Type 헤더 설정
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(parts)
                .retrieve()
                .body(AccessTokenDTo.class);

    }

}
