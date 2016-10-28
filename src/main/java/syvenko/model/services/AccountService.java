package syvenko.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import syvenko.helpers.exceptions.UserAlreadyExistsException;
import syvenko.model.Account;
import syvenko.model.dao.AccountDAO;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(Account account) throws UserAlreadyExistsException {
        if (accountDAO.findByLogin(account.getLogin())!=null){
            throw new UserAlreadyExistsException(account.getLogin());
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountDAO.create(account);
    }

    public Account findByLogin(String login){
        return accountDAO.findByLogin(login);
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
}
