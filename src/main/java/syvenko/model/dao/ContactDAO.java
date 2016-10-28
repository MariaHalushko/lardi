package syvenko.model.dao;

import syvenko.model.Account;
import syvenko.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactDAO extends GenericDAO<Contact> {
    public Contact findByIdAndUser(Long id, Account account);
    public List<Contact> findByFilter(String firstName, String lastName, String phoneNumber, Account account);
}
