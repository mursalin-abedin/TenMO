package com.techelevator.tenmo.services;

import com.techelevator.tenmo.helpers.HttpHelper;
import com.techelevator.tenmo.auth.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

public class TransferService {
    private String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private HttpEntity entity;

    public TransferService(AuthenticatedUser currentUser, String apiBaseUrl) {
        this.API_BASE_URL = apiBaseUrl;
        entity = new HttpHelper(currentUser).getHttpEntityWithTokenHeadr();
    }

    public void completeTransfer(Transfer transfer){
        HttpEntity<Transfer> postEntity = new HttpEntity<>(transfer, entity.getHeaders());
        restTemplate.exchange(API_BASE_URL + "/make_transfer", HttpMethod.POST, postEntity, String.class);
    }

    public List<Transfer> getListOfTransfers(Account accountFrom){
        HttpEntity<Account> transferEntity = new HttpEntity<>(accountFrom, entity.getHeaders());
        Transfer[] transfers = restTemplate.exchange(API_BASE_URL + "/transfers", HttpMethod.POST, transferEntity, Transfer[].class).getBody();
        return Arrays.asList(transfers);
    }

}
