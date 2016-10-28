package syvenko.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import syvenko.helpers.Views;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NamedQuery(name = Account.FIND_BY_LOGIN, query = "select u from Account u where u.login = :login")
public class Account implements Serializable {
    public static final String FIND_BY_LOGIN = "FIND_BY_LOGIN";

    public static final String LOGIN_TOO_SHORT = "Login too short";
    public static final String LOGIN_EMPTY = "Login may not be empty";
    public static final String LOGIN_FORBIDDEN = "Login has forbidden symbols";
    public static final String PASSWORD_TOO_SHORT = "Password too short";
    public static final String PASSWORD_EMPTY = "Password may not be empty";
    public static final String FULL_NAME_TOO_SHORT = "Full name too short";

    @Id
    @GeneratedValue
    @JsonView(Views.Public.class)
    private Long id;

    @Column(unique=true)
    @Length(min = 3, message = LOGIN_TOO_SHORT)
    @NotEmpty(message = LOGIN_EMPTY)
    @Pattern(regexp = "^[a-z]{0,}$" , message = LOGIN_FORBIDDEN)
    @JsonView(Views.Public.class)
    private String login;
    @Length(min = 5, message = PASSWORD_TOO_SHORT)
    @NotEmpty(message = PASSWORD_EMPTY)
    private String password;
    @Length(min = 5, message = FULL_NAME_TOO_SHORT)
    @JsonView(Views.Public.class)
    private String fullName;

    @OneToMany(mappedBy = "account",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Contact> contactList;

    public Account() {
    }

    public Account(Account account){
        super();
        id = account.id;
        login = account.login;
        password = account.password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Account{" +
                "fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", login='" + login + '\'' +
                ", id=" + id +
                '}';
    }
}
