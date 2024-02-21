package com.example.ncpmaps.service;

import com.example.ncpmaps.dto.direction.DirectionNcpResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Map;

public interface NcpMapApiService {
    // directions5
    // HTTP 요청을 나타냄
    // 이 interface와 Config를 조합해 RestClient를 사용하는 구현체를 만들어야함
    @GetExchange("/map-direction/v1/driving")
    DirectionNcpResponse directions5(
            @RequestParam
            Map<String, Object> params
    );

}
