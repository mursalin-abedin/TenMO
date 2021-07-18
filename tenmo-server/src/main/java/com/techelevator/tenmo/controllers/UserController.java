package com.techelevator.tenmo.controllers;

import com.techelevator.tenmo.daos.AccountDAO;
import com.techelevator.tenmo.daos.TransferDAO;
import com.techelevator.tenmo.daos.UserDAO;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class UserController {
    private UserDAO usersDAO;
    private AccountDAO accountDAO;
    private TransferDAO transferDAO;

    public UserController(UserDAO usersDAO, AccountDAO accountDAO, TransferDAO transferDAO) {
        this.usersDAO = usersDAO;
        this.transferDAO = transferDAO;
        this.accountDAO = accountDAO;
    }

    //account related

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    @ResponseBody
    public Account get(Principal principal) {
        return this.accountDAO.getUserAccount(principal.getName());
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> usersList(Long userId) {
        return usersDAO.usersListSubtractingCurrentUser(userId);
    }


}






