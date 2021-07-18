package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCAccount implements AccountDAO {
    private JdbcTemplate jdbcTemplate;

    public JDBCAccount(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getUserAccount(String username) {
        String sql = "SELECT account_id, accounts.user_id, balance FROM accounts" +
                " JOIN users ON users.user_id = accounts.user_id WHERE users.username = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, username);
        Account account = null;
        if (rows.next()) {
            account = mapRowToAccount(rows);
        }

        return  account;
    }

    @Override
    public List<Account> getAccountsWithUsername() {
        String sql = "SELECT account_id, balance,accounts.user_id, username FROM" +
                " accounts join users on accounts.user_id=users.user_id;";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
        List<Account> accountList = new ArrayList<>();
        while (rows.next()) {
            Account account = mapRowToAccount(rows);
            // need to add the username to account since this is joined with username
            account.setUsername(rows.getString("username"));
            //add to the list
            accountList.add(account);
        }

        return  accountList;
    }

    @Override
    public void deposit(Account account, double amount) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
        jdbcTemplate.update(sql, amount, account.getUser_id());
    }

    @Override
    public void withdraw(Account account, double amount) {
        String sql = "UPDATE accounts SET balance= balance - ? WHERE user_id = ? ";
        jdbcTemplate.update(sql, amount, account.getUser_id());
    }

    private Account mapRowToAccount(SqlRowSet rows) {
        Account account = new Account();
        account.setAccount_id(rows.getLong("account_id"));
        account.setUser_id(rows.getLong("user_id"));
        account.setBalance(rows.getDouble("balance"));
        return account;
    }

}

