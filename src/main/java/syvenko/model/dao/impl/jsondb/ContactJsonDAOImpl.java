package syvenko.model.dao.impl.jsondb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import syvenko.jsondb.JsonDB;
import syvenko.model.Account;
import syvenko.model.Contact;
import syvenko.model.dao.ContactDAO;

import java.util.List;

/**
 * Created by siven on 27.10.2016.
 */
@Repository
@Profile("json")
public class ContactJsonDAOImpl implements ContactDAO {

    @Autowired
    private JsonDB db;

    @Override
    public Contact findByIdAndUser(Long id, Account account) {
        return db.getContactWriter().findByIdAndUser(id,account);
    }

    @Override
    public List<Contact> findByFilter(String firstName, String lastName, String phoneNumber, Account account) {
        return db.getContactWriter().findByFilter(firstName,lastName,phoneNumber,account);
    }

    @Override
    public Contact findOne(long id) {
        return db.getContactWriter().findOne(id);
    }

    @Override
    public List<Contact> findAll() {
        return db.getContactWriter().getAll();
    }

    @Override
    public void create(Contact entity) {
        db.getContactWriter().create(entity);
    }

    @Override
    public Contact update(Contact entity) {
        return db.getContactWriter().update(entity);
    }

    @Override
    public void delete(Contact entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(long entityId) {
        db.getContactWriter().delete(entityId);
    }
}
