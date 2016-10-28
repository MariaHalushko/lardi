package syvenko.jsondb;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import syvenko.model.Account;
import syvenko.model.Contact;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ContactWriter {
    Path pathDb;
    ContactDB db;
    ObjectMapper mapper = new ObjectMapper();

    public ContactWriter(Path pathDb) {
        this.pathDb = pathDb;
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }

    public Contact create(Contact contact) {
        readDB();
        Long id;
        if (db.free.size()!=0){
            id = db.free.get(0);
            db.free.remove(0);
        } else {
            id = ++db.index;
        }
        contact.setId(id);
        db.contacts.add(contact);
        writeDB();
        return contact;
    }

    public Contact update(Contact contact) {
        readDB();
        Long id = contact.getId();
        Contact updated =  db.contacts.stream().filter(a -> a.getId()==id).findFirst().get();
        if (updated!=null){
            updated.update(contact);
            writeDB();
            return updated;
        }
        return null;
    }

    public List<Contact> getAll(){
        readDB();
        return db.contacts;
    }

    public void delete(Long id)  {
        readDB();
        Contact deleted =  db.contacts.stream().filter(account -> account.getId()==id).findFirst().get();
        if (deleted!=null){
            db.free.add(id);
            db.contacts.remove(deleted);
            writeDB();
        }
    }

    public Contact findOne(Long id){
        return db.contacts.stream().filter(account -> account.getId()==id).findFirst().get();
    }

    public List<Contact> findByFilter(String firstName, String lastName, String mobileNumber, Account account) {
        mobileNumber = mobileNumber.replace("(","\\(");
        mobileNumber = mobileNumber.replace(")","\\)");
        Pattern fn = Pattern.compile(".*"+firstName+".*");
        Pattern ln = Pattern.compile(".*"+lastName+".*");
        Pattern mn = Pattern.compile(".*"+mobileNumber+".*");
        readDB();
        List<Contact> result = db.contacts.stream().filter(contact -> {
            Matcher fnm = fn.matcher(contact.getFirstName());
            Matcher flm = ln.matcher(contact.getLastName());
            Matcher fmn = mn.matcher(contact.getMobilePhoneNumber());
            Matcher fhm = mn.matcher(contact.getHomePhoneNumber());
            boolean r = (fnm.matches()&flm.matches())&(fmn.matches()|fhm.matches());
            return r&contact.getAccount().getId()==account.getId();
        }).collect(Collectors.toList());
        return result;
    }

    public Contact findByIdAndUser(Long id, Account account){
        Contact contact = findOne(id);
        if (contact.getAccount().getId()!=account.getId()){
            return null;
        }
        return contact;
    }

    private void readDB() {
        try {
            db = mapper.readValue(Files.readAllBytes(pathDb),ContactDB.class);
        } catch (JsonParseException e) {
            db = new ContactDB();
        } catch (JsonMappingException e) {
            db = new ContactDB();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeDB(){
        try {
            mapper.writeValue(pathDb.toFile(),db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
