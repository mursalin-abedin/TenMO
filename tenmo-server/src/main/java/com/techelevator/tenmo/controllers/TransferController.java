package com.techelevator.tenmo.controllers;

import com.techelevator.tenmo.daos.AccountDAO;
import com.techelevator.tenmo.daos.TransferDAO;
import com.techelevator.tenmo.daos.UserDAO;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController

public class TransferController {

    private UserDAO usersDAO;
    private AccountDAO accountDAO;
    private TransferDAO transferDAO;

    public TransferController(UserDAO usersDAO, AccountDAO accountDAO, TransferDAO transferDAO) {
        this.usersDAO = usersDAO;
        this.transferDAO = transferDAO;
        this.accountDAO = accountDAO;
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    @ResponseBody
    public List<Transfer> transferList(@RequestBody Account accountFrom) {
        return transferDAO.transferList(accountFrom.getAccount_id());
    }

    @RequestMapping(path = "/make_transfer", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void makeTransfer(@RequestBody Transfer transfer) {
        transferDAO.transferMoney(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
    }

    // TRYING TO MAKE REQUESTS WORK
    @RequestMapping(path = "/request_transfer", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void requestTransfer(@RequestBody Transfer transfer) {
        transferDAO.requestMoney(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
    }

    @RequestMapping (path="/accountList_with_username")
    @ResponseBody
    public List<Account> accountsWithUsername() {
       return accountDAO.getAccountsWithUsername();
    }

}
