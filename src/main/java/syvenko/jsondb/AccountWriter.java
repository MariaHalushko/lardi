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

public class AccountWriter {
    Path pathDb;
    Path pathContactDB;
    AccountDB db;
    ContactDB contactDB;
    ObjectMapper mapper = new ObjectMapper();

    public AccountWriter(Path db,Path contactDB) {
        this.pathDb = db;
        this.pathContactDB = contactDB;
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }

    public Account create(Account account) {
        readDB();
        Long id;
        if (db.free.size()!=0){
            id = db.free.get(0);
            db.free.remove(0);
        } else {
            id = ++db.index;
        }
        account.setId(id);
        db.accounts.add(account);
        writeDB();
        return account;
    }

    public Account update(Account account) {
        readDB();
        Long id = account.getId();
        Account updated =  db.accounts.stream().filter(a -> a.getId()==id).findFirst().get();
        if (updated!=null){
            updated.setLogin(account.getLogin());
            updated.setPassword(account.getPassword());
            updated.setFullName(account.getFullName());
            writeDB();
            return updated;
        }
        return null;
    }

    public List<Account> getAll(){
        readDB();
        return db.accounts;
    }

    public void delete(Long id)  {
        readDB();
        Account deleted =  db.accounts.stream().filter(account -> account.getId()==id).findFirst().get();
        if (deleted!=null){
            db.free.add(id);
            db.accounts.remove(deleted);
            writeDB();
        }
    }

    public Account findOne(Long id){
        return db.accounts.stream().filter(account -> account.getId()==id).findFirst().get();
    }

    public Account findByLogin(String login) {
        readDB();
        Account account = db.accounts.stream().filter(a -> a.getLogin().equals(login)).findFirst().get();
        return account;
    }


    private void readDB() {
        try {
            db = mapper.readValue(Files.readAllBytes(pathDb),AccountDB.class);
        } catch (JsonParseException e) {
            db = new AccountDB();
        } catch (JsonMappingException e) {
            db = new AccountDB();
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

    private void cleanCascade(Long id){
        try {
            contactDB = mapper.readValue(Files.readAllBytes(pathContactDB),ContactDB.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        contactDB.contacts.stream().filter(c -> c.getAccount().getId() == id).forEach(c -> {
            contactDB.contacts.remove(c);
        });
        try {
            mapper.writeValue(pathContactDB.toFile(),contactDB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
