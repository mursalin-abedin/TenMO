package com.techelevator.tenmo;

import com.techelevator.tenmo.auth.models.AuthenticatedUser;
import com.techelevator.tenmo.auth.models.UserCredentials;
import com.techelevator.tenmo.auth.services.AuthenticationService;
import com.techelevator.tenmo.auth.services.AuthenticationServiceException;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
    private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
    private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
    private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
    private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
    private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
    private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
    private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;

    //services

    private AccountService accountService;
    private TransferService transferService;
    private List<Account> accountList;


    public static void main(String[] args) {
        App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
        app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
        this.console = console;
        this.authenticationService = authenticationService;
    }

    public void run() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");

        registerAndLogin();
        // put services here

        this.accountService= new AccountService(currentUser, API_BASE_URL);
        this.transferService= new TransferService(currentUser, API_BASE_URL);
        accountList = accountService.getListOfUserAccounts();

        mainMenu();
    }

    private void mainMenu() {
        while(true) {
            String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
                viewCurrentBalance();
            } else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
                viewTransferHistory();
            } else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
                viewPendingRequests();
            } else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
                sendBucks();
            } else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
                requestBucks();
            } else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else {
                // the only other option on the main menu is to exit
                exitProgram();
            }
        }
    }

    private void
    viewCurrentBalance() {
        Account account = accountService.getCurrentBalance();
        console.displayAccountBalance(account);
    }

    private void viewTransferHistory() {
        Account usersAccount = filterAccountByUserId(currentUser.getUser().getId(), accountList);
        List<Transfer> transfers = transferService.getListOfTransfers(usersAccount);

        console.displayAllTransfers(transfers, usersAccount);
        long transferId = console.getTransferId();

        if (transferId == 0) {
            return;
        }

        Transfer transferRequested = filterTransferByTransferId(transferId, transfers);

        if (!transfers.contains(transferRequested)){
            console.incorrectTransferId();
            return;
        }

        console.displayTransferDetails(transferRequested);

    }

    private void viewPendingRequests() {
        Account accountFrom = filterAccountByUserId(currentUser.getUser().getId(), accountList);
        List<Transfer> transfers = transferService.getListOfTransfers(accountFrom);
        long pendingStatusId = 1;

        console.displayPendingTransfers(transfers, pendingStatusId);
        long transferId = console.getPendingTransferId();

        if (transferId == 0) {
            return;
        }

        Transfer transferRequested = filterTransferByTransferId(transferId, transfers);
        int transferDecision = console.approveOrRejectTransfer();

        if (transferDecision == 2){
            transferRequested.setTransferStatusId(Long.parseLong("3"));
        } else if (transferDecision == 1){
            transferRequested.setTransferStatusId(Long.parseLong("2"));
        }

    }

    private void sendBucks() {
        //get list of account

        //display list of users opt-out current user
        console.displayOtherUsers(accountList, currentUser.getUser().getUsername());
        //get the  user ID to transfer money to
        long userIdInput = console.getTransferUserIdInput();

        if (userIdInput == 0) {
            return;
        }

        double transferAmount = console.getTransferAmount();
        //create acountfrom
        Account accountFrom = filterAccountByUserId(currentUser.getUser().getId(), accountList);

        if (accountFrom.getBalance() - transferAmount < 0) {
            console.insufficientFundInput();
            return;
        } else {
            Account accountTo = filterAccountByUserId((int)userIdInput, accountList);

            if (!accountList.contains(accountTo)){
                console.incorrectUserId();
                return;
            }
            //Make a transfer object to hold the transfer info
            Transfer transfer = new Transfer();
            transfer.setAmount(transferAmount);
            transfer.setAccountTo(accountTo);
            transfer.setAccountFrom(accountFrom);
            //make transfer
            transferService.completeTransfer(transfer);
        }

    }

    private void requestBucks() {
        // TODO Auto-generated method stub

    }

    private void exitProgram() {
        System.exit(0);
    }

    private void registerAndLogin() {
        while(!isAuthenticated()) {
            String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
            if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
                register();
            } else {
                // the only other option on the login menu is to exit
                exitProgram();
            }
        }
    }

    private boolean isAuthenticated() {
        return currentUser != null;
    }

    private void register() {
        System.out.println("Please register a new user account");
        boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                authenticationService.register(credentials);
                isRegistered = true;
                System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
                System.out.println("REGISTRATION ERROR: "+e.getMessage());
                System.out.println("Please attempt to register again.");
            }
        }
    }

    private void login() {
        System.out.println("Please log in");
        currentUser = null;
        while (currentUser == null) //will keep looping until user is logged in
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                currentUser = authenticationService.login(credentials);
            } catch (AuthenticationServiceException e) {
                System.out.println("LOGIN ERROR: "+e.getMessage());
                System.out.println("Please attempt to login again.");
            }
        }
    }

    private UserCredentials collectUserCredentials() {
        String username = console.getUserInput("Username");
        String password = console.getUserInput("Password");
        return new UserCredentials(username, password);
    }

    public Account filterAccountByUserId(int user_id, List<Account> accountList) {
        Account account = null;

        for (Account acc: accountList) {
            if(acc.getUser_id() == user_id)
                account = acc;
        }

        return account;
    }

    public Transfer filterTransferByTransferId(long transferId, List<Transfer> transferList){
        Transfer transfer = null;

        for (Transfer desiredTransfer: transferList) {
            if (desiredTransfer.getTransferId() == transferId)
                transfer = desiredTransfer;
        }

        return transfer;
    }


}