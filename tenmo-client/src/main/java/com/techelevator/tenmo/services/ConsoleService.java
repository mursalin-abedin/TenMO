package com.techelevator.tenmo.services;

import com.techelevator.tenmo.auth.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private PrintWriter out;
    private Scanner in;
    private AuthenticatedUser currentUser;

    public ConsoleService(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output, true);
        this.in = new Scanner(input);
    }

    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        out.println();
        return choice;
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.valueOf(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if (choice == null) {
            out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        return choice;
    }

    private void displayMenuOptions(Object[] options) {
        out.println();
        for (int i = 0; i < options.length; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + options[i]);
        }
        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }

    public String getUserInput(String prompt) {
        out.print(prompt + ": ");
        out.flush();
        return in.nextLine();
    }

    public Integer getUserInputInteger(String prompt) {
        Integer result = null;
        do {
            out.print(prompt + ": ");
            out.flush();
            String userInput = in.nextLine();
            try {
                result = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
            }
        } while (result == null);
        return result;
    }


    public void displayAccountBalance(Account account) {
        out.print("Your current balance is : $");
        out.printf("%.2f", account.getBalance());
        out.println();
        out.flush();
    }

    public void displayOtherUsers(List<Account> users, String username) {
        out.println("-------------------------------------------");
        out.println("Users");
        out.printf("%-30s", "ID");
        out.printf("%-10s", "Name");
        out.println();
        out.println("-------------------------------------------");

        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).getUsername().equals(username)) {
                out.printf("%-30s", users.get(i).getUser_id());
                out.printf("%-10s ", users.get(i).getUsername());
                out.println();
            }

        }
        out.println("-------------------------------------------");
        out.flush();

    }

    public long getTransferUserIdInput() {
        out.println("Enter ID of user you are sending to (0 to cancel): ");
        long userIdChoice = Long.parseLong(in.nextLine());
        out.flush();
        return userIdChoice;
    }


    public double getTransferAmount() {
        out.println("Enter amount: ");
        double transferAmount = Double.parseDouble(in.nextLine());
        out.flush();
        return transferAmount;
    }

    public void insufficientFundInput(){
        out.println();
        out.println(" *** Not enough funds available. Please try again! *** ");
        out.flush();
    }

    public void displayAllTransfers(List<Transfer> transfers, Account usersAccount) {
        out.println("-------------------------------------------");
        out.println("Transfers");
        out.printf("%-20s", "ID");
        out.printf("%-15s", "From/To");
        out.printf("%-10s", "Amount");
        out.println();
        out.println("-------------------------------------------");

        for (int i = 0; i < transfers.size(); i++) {
            if (transfers.get(i).getSender().equals(usersAccount.getUsername())){
                out.printf("%-20s", transfers.get(i).getTransferId());
                out.printf("%-3s", "To: ");
                out.printf("%-10s", transfers.get(i).getReceiver());
                out.print("$ ");
                out.printf("%.2f", transfers.get(i).getAmount());
                out.println();
            } else if (transfers.get(i).getReceiver().equals(usersAccount.getUsername())){
                out.printf("%-20s", transfers.get(i).getTransferId());
                out.printf("%-3s", "From: ");
                out.printf("%-8s", transfers.get(i).getSender());
                out.print("$ ");
                out.printf("%.2f", transfers.get(i).getAmount());
                out.println();
            }
        }

        out.println("---------");
        out.flush();

    }

    public long getTransferId() {
        out.println("Please enter transfer ID to view details (0 to cancel): ");
        long transferId = Long.parseLong(in.nextLine());
        out.flush();
        return transferId;
    }

    public void displayTransferDetails(Transfer transfer) {
        out.println("-------------------------------------------");
        out.println("Transfer Details");
        out.println("-------------------------------------------");

        if (transfer.getTransferStatusId() == 1 && transfer.getTransferTypeId() == 2){
            out.println("Id: " + transfer.getTransferId());
            out.println("From: " + transfer.getSender());
            out.println("To: " + transfer.getReceiver());
            out.println("Type: Send");
            out.println("Status: Pending");
            out.printf("Amount: ");
            out.printf("%.2f", transfer.getAmount());
            out.println();
        } else if (transfer.getTransferStatusId() == 2 && transfer.getTransferTypeId() == 2){
            out.println("Id: " + transfer.getTransferId());
            out.println("From: " + transfer.getSender());
            out.println("To: " + transfer.getReceiver());
            out.println("Type: Send");
            out.println("Status: Approved");
            out.printf("Amount: ");
            out.printf("%.2f", transfer.getAmount());
            out.println();
        }
        out.flush();

    }

    public void displayPendingTransfers(List<Transfer> transfers, Long transferStatusId) {
        out.println("-------------------------------------------");
        out.println("Pending Transfers");
        out.printf("%-20s", "ID");
        out.printf("%-15s", "To");
        out.printf("%-10s", "Amount");
        out.println();
        out.println("-------------------------------------------");

        for (int i = 0; i < transfers.size(); i++) {
            if (transfers.get(i).getTransferStatusId().equals(transferStatusId)){
                out.printf("%-20s", transfers.get(i).getTransferId());
                out.printf("%-14s", transfers.get(i).getReceiver());
                out.print("$ ");
                out.printf("%.2f", transfers.get(i).getAmount());
                out.println();
            }
        }

        out.println("---------");
        out.flush();

    }

    public long getPendingTransferId() {
        out.println("Please enter transfer ID to approve/reject (0 to cancel): ");
        long transferId = Long.parseLong(in.nextLine());
        out.flush();
        return transferId;
    }

    public int approveOrRejectTransfer() {
        out.println();
        out.println("1: Approve");
        out.println("2: Reject");
        out.println("0: Don't approve or reject");
        out.println("---------");
        out.println("Please choose an option: ");
        int decision = Integer.parseInt(in.nextLine());
        out.flush();
        return decision;
    }

    public void incorrectUserId() {
        out.println();
        out.println(" *** User ID does not exist! *** ");
        out.flush();
    }

    public void incorrectTransferId() {
        out.println();
        out.println(" *** Transfer ID does not exist! *** ");
        out.flush();
    }
}
