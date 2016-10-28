package syvenko.jsondb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonDB {
    public static final String ACCOUNT_DB_NAME = "accountDB.json";
    public static final String CONTACT_DB_NAME = "contactDB.json";

    private Path path;
    private Path pathAccountDB;
    private Path pathContactDB;

    private AccountWriter accountWriter;
    private ContactWriter contactWriter;

    public JsonDB(String path) {
        this.path = Paths.get(path);
        this.pathAccountDB = Paths.get(path,ACCOUNT_DB_NAME);
        this.pathContactDB = Paths.get(path,CONTACT_DB_NAME);
    }

    public void init() throws IOException {
        if (!Files.exists(path)){
            Files.createDirectory(path);
            Files.createFile(pathAccountDB);
            Files.createFile(pathContactDB);
        } else {
            if (!Files.isDirectory(path)){
                throw new IllegalArgumentException("Check path to DB path!");
            }
            if (!Files.exists(pathAccountDB)){
                Files.createFile(pathAccountDB);
            }
            if (!Files.exists(pathContactDB)){
                Files.createFile(pathContactDB);
            }
            accountWriter = new AccountWriter(pathAccountDB,pathContactDB);
            contactWriter = new ContactWriter(pathContactDB);
        }
    }

    public AccountWriter getAccountWriter(){
        return accountWriter;
    }

    public ContactWriter getContactWriter(){
        return contactWriter;
    }
}
