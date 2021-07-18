package com.techelevator.tenmo.services;

import com.techelevator.tenmo.helpers.HttpHelper;
import com.techelevator.tenmo.auth.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Account;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

public class AccountService {

    private String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private HttpEntity entity;


    public AccountService(AuthenticatedUser currentUser, String apiBaseUrl) {
        this.API_BASE_URL = apiBaseUrl;
        entity = new HttpHelper(currentUser).getHttpEntityWithTokenHeadr();
    }


    public Account getCurrentBalance(){
        Account account = restTemplate.exchange(API_BASE_URL + "accounts", HttpMethod.GET, entity, Account.class).getBody();

        return account;
    }

    public List<Account> getListOfUserAccounts(){
        Account[] users = restTemplate.exchange(API_BASE_URL + "accountList_with_username", HttpMethod.GET, entity, Account[].class).getBody();

        return Arrays.asList(users);

    }


}
