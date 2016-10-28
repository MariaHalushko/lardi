package syvenko.model.dao;

import syvenko.model.Account;

import java.util.Optional;

public interface AccountDAO extends GenericDAO<Account> {
    public Account findByLogin(String login);
}
