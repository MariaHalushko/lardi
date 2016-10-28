package syvenko.model.dao.impl.jsondb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import syvenko.jsondb.JsonDB;
import syvenko.model.Account;
import syvenko.model.dao.AccountDAO;

import java.util.List;

@Repository
@Profile("json")
public class AccountJsonDAOImpl implements AccountDAO {

    @Autowired
    private JsonDB db;

    @Override
    public Account findByLogin(String login) {
        return db.getAccountWriter().findByLogin(login);
    }

    @Override
    public Account findOne(long id) {
        return db.getAccountWriter().findOne(id);
    }

    @Override
    public List<Account> findAll() {
        return db.getAccountWriter().getAll();
    }

    @Override
    public void create(Account entity) {
        db.getAccountWriter().create(entity);
    }

    @Override
    public Account update(Account entity) {
        return db.getAccountWriter().update(entity);
    }

    @Override
    public void delete(Account entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(long entityId) {
        db.getAccountWriter().delete(entityId);
    }
}
