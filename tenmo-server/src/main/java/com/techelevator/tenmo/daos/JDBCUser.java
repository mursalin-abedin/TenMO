package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCUser implements UserDAO {
    private JdbcTemplate jdbcTemplate;

    public JDBCUser(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> usersListSubtractingCurrentUser(Long userId) {
        String sqlAllUsers = "SELECT user_id, username, password_hash FROM users" +
                " EXCEPT SELECT user_id, username, password_hash FROM users WHERE user_id = ?";
        List<User> users = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlAllUsers, userId);
        while (rows.next()) {
            users.add(mapRawToUsers(rows));
        }
        return users;
    }

    private User mapRawToUsers(SqlRowSet row) {
        User user = new User();
        user.setUser_id(row.getLong("user_id"));
        user.setUsername(row.getString("username"));
        user.setPassword_hash(row.getString("password_hash"));
        return user;
    }

}
