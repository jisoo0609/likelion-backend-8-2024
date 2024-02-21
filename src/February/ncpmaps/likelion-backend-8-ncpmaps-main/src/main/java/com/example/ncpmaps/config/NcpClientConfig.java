package com.example.ncpmaps.config;

import com.example.ncpmaps.service.NcpMapApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Slf4j
@Configuration
public class NcpClientConfig {
    private static final String NCP_APIGW_API_KEY_ID = "X-NCP-APIGW-API-KEY-ID";
    private static final String NCP_APIGW_API_KEY = "X-NCP-APIGW-API-KEY";

    // application.yaml에서 설정해야 함
    @Value("${ncp.api.client-id}")
    private String ncpMapClientId;
    @Value("${ncp.api.client-secret}")
    private String ncpMapClientSecret;

    @Bean
    public RestClient ncpMapClient() {
        return RestClient.builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com")
                .defaultHeader(NCP_APIGW_API_KEY_ID, ncpMapClientId)
                .defaultHeader(NCP_APIGW_API_KEY, ncpMapClientSecret)
                .build();
    }

    // HTTP Interface의 구현체가 Bean 객체로 등록된다.
    @Bean
    public NcpMapApiService mapApiService() {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(ncpMapClient()))
                .build()
                .createClient(NcpMapApiService.class);
    }

}
