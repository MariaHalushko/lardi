package syvenko.model.dao.impl.jpa;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import syvenko.model.Account;
import syvenko.model.dao.AccountDAO;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
@Profile("jpa")
public class AccountJpaDAOImpl extends JpaDAO<Account> implements AccountDAO {

    public AccountJpaDAOImpl() {
        setClazz(Account.class);
    }

    @Override
    public Account findByLogin(String login) {
        TypedQuery<Account> query = entityManager.createNamedQuery(Account.FIND_BY_LOGIN,Account.class)
                .setParameter("login",login);
        try {
            return query.getSingleResult();

        } catch (NoResultException e){
            return null;
        }
    }
}
