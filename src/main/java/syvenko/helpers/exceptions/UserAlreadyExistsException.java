package syvenko.helpers.exceptions;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String login) {
        super("Account with login "+login+" already exists");
    }
}
