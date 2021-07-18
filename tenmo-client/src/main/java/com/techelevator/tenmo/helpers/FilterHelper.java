package com.techelevator.tenmo.helpers;

import com.techelevator.tenmo.models.Account;
import java.util.List;

public class FilterHelper {

    public Account filterAccountUsingUserId(int user_id, List<Account> accountList) {
        Account account = null;

        for (Account acc: accountList) {
            if(acc.getUser_id() == user_id)
                account = acc;
        }
        return account;
    }

    public Account filterAccountFromListByAccountId(int account_id, List<Account> accountList) {
        Account account = null;

        for (Account acc: accountList) {
            if(acc.getAccount_id() == account_id)
                account = acc;
        }
        return account;
    }

}
