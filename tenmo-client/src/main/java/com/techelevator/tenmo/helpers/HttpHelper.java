package com.techelevator.tenmo.helpers;

import com.techelevator.tenmo.auth.models.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class HttpHelper {
    private RestTemplate restTemplate;
    private AuthenticatedUser currentUser;

    public HttpHelper(AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
    }

    public HttpEntity getHttpEntityWithTokenHeadr() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);

        return entity;

    }

}
