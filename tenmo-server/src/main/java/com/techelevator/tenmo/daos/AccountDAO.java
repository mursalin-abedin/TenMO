package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Account;

import java.util.List;

public interface AccountDAO {

    public Account getUserAccount(String username);
    public void deposit(Account account,double amount) ;
    public void withdraw(Account account,double amount) ;
    public List<Account> getAccountsWithUsername();

}
