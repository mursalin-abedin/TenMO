package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import java.util.List;

public interface TransferDAO {

    public void transferMoney(Account accountFrom, Account accountTo, double amount);
    public List<Transfer> transferList(Long accountFrom);
    public void requestMoney(Account accountFrom, Account accountTo, double amount);

}
