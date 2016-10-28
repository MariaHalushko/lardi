package syvenko.jsondb;


import syvenko.model.Contact;

import java.util.LinkedList;
import java.util.List;

public class ContactDB extends Index {
    public List<Contact> contacts = new LinkedList<>();
}
