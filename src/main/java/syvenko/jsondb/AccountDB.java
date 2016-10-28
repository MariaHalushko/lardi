package syvenko.jsondb;



import syvenko.model.Account;

import java.util.LinkedList;
import java.util.List;

public class AccountDB extends Index {
    public List<Account> accounts = new LinkedList<>();
}
