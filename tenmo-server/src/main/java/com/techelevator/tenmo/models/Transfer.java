package com.techelevator.tenmo.models;

public class Transfer {
    private Long transferId;
    private Long transferTypeId;
    private Long transferStatusId;
    private Long accountFrom_id;
    private Long accountTo_id;
    private double amount;
    // derived privates from join tables
    private String sender;
    private String receiver;
    // may need these to make transfer
    private Account accountFrom;
    private Account accountTo;


    public Transfer() {

    }

    public Transfer(Long transferId, Long transferTypeId, Long transferStatusId,
                    Long accountFrom_id, Long accountTo_id, double amount, Account accountFrom, Account accountTo) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom_id = accountFrom_id;
        this.accountTo_id = accountTo_id;
        this.amount = amount;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public Long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public Long getAccountFrom_id() {
        return accountFrom_id;
    }

    public void setAccountFrom_id(Long accountFrom_id) {
        this.accountFrom_id = accountFrom_id;
    }

    public Long getAccountTo_id() {
        return accountTo_id;
    }

    public void setAccountTo_id(Long accountTo_id) {
        this.accountTo_id = accountTo_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
