package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCTransfer implements TransferDAO {
    private JdbcTemplate jdbcTemplate;
    private JDBCAccount accountDao;
    private JDBCUser jdbcUser;

    public JDBCTransfer(JdbcTemplate jdbcTemplate, JDBCAccount accountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
    }

    @Override
    public void transferMoney(Account accountFrom, Account accountTo, double amount) {
        String sql = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, " +
                "account_from, account_to, amount) VALUES (DEFAULT, 2, 2, ?, ?, ?)";
        jdbcTemplate.update(sql, accountFrom.getAccount_id(), accountTo.getAccount_id(), amount);
        //substract from accountFrom and add to accountTo
        accountDao.deposit(accountTo, amount);
        accountDao.withdraw(accountFrom, amount);
    }

    @Override
    public void requestMoney(Account accountFrom, Account accountTo, double amount) {
        String sql = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, " +
                "account_from, account_to, amount) VALUES (DEFAULT, 1, 1, ?, ?, ?)";
        jdbcTemplate.update(sql, accountFrom.getAccount_id(), accountTo.getAccount_id(), amount);
    }

    @Override
    public List<Transfer> transferList(Long usersAccountId) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, amount, user_from.username AS sender," +
                " user_to.username AS receiver, transfers.account_from, transfers.account_to FROM transfers" +
                " JOIN accounts AS account_from ON transfers.account_from = account_from.account_id JOIN accounts" +
                " AS account_to ON transfers.account_to = account_to.account_id JOIN users AS user_from ON " +
                "account_from.user_id = user_from.user_id JOIN users AS user_to ON account_to.user_id = " +
                "user_to.user_id WHERE account_from = ? OR account_to = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, usersAccountId, usersAccountId);
        List<Transfer> transferList = new ArrayList<>();

        while (rows.next()) {
            Transfer transfer = new Transfer();
            transfer.setTransferId(rows.getLong("transfer_id"));
            transfer.setTransferTypeId(rows.getLong("transfer_type_id"));
            transfer.setTransferStatusId(rows.getLong("transfer_status_id"));
            transfer.setAmount(rows.getDouble("amount"));
            transfer.setSender(rows.getString("sender"));
            transfer.setReceiver(rows.getString("receiver"));
            transfer.setAccountFrom_id(rows.getLong("account_from"));
            transfer.setAccountTo_id(rows.getLong("account_to"));
            transferList.add(transfer);
        }
        return transferList;
    }

    private Transfer mapToTransfer(SqlRowSet rows) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rows.getLong("transfer_id"));
        transfer.setTransferTypeId(rows.getLong("transfer_type_id"));
        transfer.setTransferStatusId(rows.getLong("transfer_status_id"));
        transfer.setAccountFrom_id(rows.getLong("account_from"));
        transfer.setAccountTo_id(rows.getLong("account_to"));
        transfer.setAmount(rows.getDouble("amount"));
        return transfer;
    }

}




