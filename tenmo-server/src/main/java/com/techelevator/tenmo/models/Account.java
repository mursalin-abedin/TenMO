package com.techelevator.tenmo.models;

public class Account {
  private long account_id;
  private long user_id;
  private double balance;
  private String username;

    public Account(){

     }

    public Account(long account_id, long user_id, double balance) {
        this.account_id = account_id;
        this.user_id = user_id;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
