package syvenko.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import syvenko.helpers.Views;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NamedQueries({
        @NamedQuery(name = Contact.FIND_BY_ID_AND_USER, query = "select c from Contact c where c.id = :id  and c.account = :user"),
        @NamedQuery(name = Contact.FIND_BY_FIRST_NAME_LAST_NAME_MOBILE_NUMBER, query = "select c from Contact c where c.firstName like :firstname and c.lastName like :lastname and (c.mobilePhoneNumber like :phonenumber or c.homePhoneNumber like :phonenumber) and c.account = :account")
})
public class Contact implements Serializable{

    public static final String FIND_BY_ID_AND_USER = "IND_BY_ID_AND_USER";
    public static final String FIND_BY_FIRST_NAME_LAST_NAME_MOBILE_NUMBER = "FIND_BY_FIRST_NAME_LAST_NAME_MOBILE_NUMBER";

    public static final String INVALID_MOBILE = "Not valid mobile phone number, pattern '+380(yy)xxxxxxx'";
    public static final String INVALID_HOME = "Not valid home phone number, pattern '0(yy)xxxxxxx'";
    public static final String INVALID_EMAIL = "Not valid email";
    public static final String TOO_SHORT = "* name too short, min length = 4";

    @Id
    @GeneratedValue
    @JsonView(Views.Public.class)
    private Long id;

    @NotEmpty(message = TOO_SHORT)
    @Length(min = 4, message = TOO_SHORT)
    @JsonView(Views.Public.class)
    private String firstName;
    @NotEmpty(message = TOO_SHORT)
    @Length(min = 4, message = TOO_SHORT)
    @JsonView(Views.Public.class)
    private String lastName;
    @NotEmpty(message = TOO_SHORT)
    @Length(min = 4, message = TOO_SHORT)
    @JsonView(Views.Public.class)
    private String middleName;
    @Pattern(regexp = "^((0)-?)?(\\(?\\d{2}\\)?)?-?\\d{7}$", message = INVALID_HOME)
    @Length(min = 12, max = 12, message = INVALID_HOME)
    @JsonView(Views.Public.class)
    private String homePhoneNumber;

    @NotEmpty
    @Pattern(regexp = "^(\\+380-?)?(\\(?\\d{2}\\)?)?-?\\d{7}$", message = INVALID_MOBILE)
    @Length(min = 15, max = 15, message = INVALID_MOBILE)
    @JsonView(Views.Public.class)
    private String mobilePhoneNumber;
    @JsonView(Views.Public.class)
    private String address;
    @Email(message = INVALID_EMAIL)
    @JsonView(Views.Public.class)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void update(Contact contact){
        firstName = contact.firstName;
        middleName = contact.middleName;
        lastName = contact.lastName;
        address = contact.address;
        email = contact.email;
        homePhoneNumber = contact.homePhoneNumber;
        mobilePhoneNumber = contact.mobilePhoneNumber;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", homePhoneNumber='" + homePhoneNumber + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
