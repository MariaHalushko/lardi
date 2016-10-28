package syvenko.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import syvenko.helpers.exceptions.ResourcesNotFoundException;
import syvenko.model.Account;
import syvenko.model.Contact;
import syvenko.model.dao.ContactDAO;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactService {
    @Autowired
    private ContactDAO contactDAO;

    public Contact createEntry(Contact contact, Account account){
        contact.setAccount(account);
        contactDAO.create(contact);
        return contact;
    }

    public List<Contact> getList(String firstName, String lastName, String mobileNumber, Account account){
        firstName = (firstName == null)?"":firstName;
        lastName = (lastName == null)?"":lastName;
        mobileNumber = (mobileNumber == null)?"":mobileNumber;
        return contactDAO.findByFilter(firstName,lastName,mobileNumber,account);
    }

    public Contact getOne(Long id, Account account) throws ResourcesNotFoundException {
        Contact contact = contactDAO.findByIdAndUser(id, account);
        if (contact == null){
            throw new ResourcesNotFoundException();
        }
        return contact;
    }

    public void delete(Long id, Account account) throws ResourcesNotFoundException {
        Contact contact = contactDAO.findByIdAndUser(id, account);
        if (contact == null){
            throw new ResourcesNotFoundException();
        }
        contactDAO.delete(contact);
    }

    public Contact update(Long id, Contact contact, Account account) throws ResourcesNotFoundException {
        Contact updated = contactDAO.findByIdAndUser(id, account);
        if (updated == null){
            throw new ResourcesNotFoundException();
        }
        updated.update(contact);
        contactDAO.update(updated);
        return updated;
    }

    public ContactDAO getContactDAO() {
        return contactDAO;
    }

    public void setContactDAO(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }
}
