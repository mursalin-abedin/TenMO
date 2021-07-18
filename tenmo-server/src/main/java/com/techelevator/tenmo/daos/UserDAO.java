package com.techelevator.tenmo.daos;

import com.techelevator.tenmo.models.User;

import java.util.List;

public interface UserDAO {
    public List<User> usersListSubtractingCurrentUser(Long userId) ;
}
