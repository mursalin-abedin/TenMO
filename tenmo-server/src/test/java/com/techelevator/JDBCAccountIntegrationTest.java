package com.techelevator;

import com.techelevator.tenmo.daos.JDBCAccount;
import com.techelevator.tenmo.models.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JDBCAccountIntegrationTest extends DAOIntegrationTest{
    private JdbcTemplate jdbcTemplate;
    private JDBCAccount jdbcAccount;

  @Before
  public void setupBeforeTest() {
      jdbcTemplate = new JdbcTemplate(getDataSource());
      jdbcAccount = new JDBCAccount(jdbcTemplate);

  }

  @Test

    public void retrieve_user_Account_with_User_name() {
     List<Account> originalAccount = jdbcAccount.getAccountsWithUsername();

      String sql = "INSERT INTO users (user_id, username, password_hash) VALUES (4001, 'testUser', 'testPassword')";
      jdbcTemplate.update(sql);

      List<Account> updatedAccount = jdbcAccount.getAccountsWithUsername();

      Assert.assertEquals(originalAccount.size()+1, updatedAccount);

  }
}
