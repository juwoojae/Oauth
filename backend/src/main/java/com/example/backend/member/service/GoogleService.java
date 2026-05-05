package com.example.backend.member.service;

import com.example.backend.member.dto.AccessTokenDto;
import com.example.backend.member.dto.GoogleProfileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class GoogleService {

    @Value("${oauth2.google.client-id}")
    private String googleClientId;

    @Value("${oauth2.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth2.google.redirect-uri}")
    private String googleRedirectUri;

    /**
     * accessToken 을 받기 위해서 우리가 google 에 요청해야할 것들
     * 인가 코드, clientId, client_secret, redirect_uri, grant_type
     */
    public AccessTokenDto getAccessToken(String code){

        //Spring6 부터 RestTemplate 비추천 상태이기에, 대신 RestClient 사용
        RestClient restClient = RestClient.create();

        //        MultiValueMap을 통해 자동으로 form-data형식으로 body 조립 가능
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("redirect_uri", googleRedirectUri);
        params.add("grant_type", "authorization_code");

        ResponseEntity<AccessTokenDto> response = restClient.post()
                  .uri("https://oauth2.googleapis.com/token")
                  .header("Content-Type", "application/x-www-form-urlencoded")
//                ?code=xxxx&client_id=yyyy&
                  .body(params)
                  //    응답 body 값만을 추출
                  .retrieve()
                  .toEntity(AccessTokenDto.class);

        System.out.println("응답 json" + response.getBody());

        return response.getBody();
    }

    /**
     * 엑세스 토큰으로 구글에서 클라이언트 프로필 받아오기
     */
    public GoogleProfileDto getGoogleProfile(String token){
        RestClient restClient = RestClient.create();
        ResponseEntity<GoogleProfileDto> response =  restClient.get()
                .uri("https://openidconnect.googleapis.com/v1/userinfo")
                .header("Authorization", "Bearer "+token)
                .retrieve()
                .toEntity(GoogleProfileDto.class);
        System.out.println("profile JSON" + response.getBody());

        return response.getBody();
    }
}
