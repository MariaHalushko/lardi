package syvenko.model.dao.impl.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import syvenko.model.Contact;
import syvenko.model.Account;
import syvenko.model.dao.ContactDAO;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Profile("jpa")
public class ContactJpaDAOImpl extends JpaDAO<Contact> implements ContactDAO {
    public ContactJpaDAOImpl() {
        setClazz(Contact.class);
    }

    @Override
    public Contact findByIdAndUser(Long id, Account account) {
        TypedQuery<Contact> query = entityManager.createNamedQuery(Contact.FIND_BY_ID_AND_USER,Contact.class)
                .setParameter("id",id)
                .setParameter("user", account);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<Contact> findByFilter(String firstName, String lastName, String phoneNumber, Account account) {
        TypedQuery<Contact> query = entityManager.createNamedQuery(Contact.FIND_BY_FIRST_NAME_LAST_NAME_MOBILE_NUMBER,Contact.class)
                .setParameter("firstname","%"+firstName+"%")
                .setParameter("lastname","%"+lastName+"%")
                .setParameter("phonenumber","%"+ phoneNumber +"%")
                .setParameter("account",account);
        List<Contact> list = query.getResultList();
        return list;
    }


}
