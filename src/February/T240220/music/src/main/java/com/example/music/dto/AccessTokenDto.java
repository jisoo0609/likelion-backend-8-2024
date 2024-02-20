package com.example.music.dto;

// jackson 라이브러리에서 변환
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccessTokenDto {
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
}
