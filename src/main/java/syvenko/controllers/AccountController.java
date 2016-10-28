package syvenko.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import syvenko.helpers.Views;
import syvenko.helpers.errors.Error;
import syvenko.helpers.errors.ErrorBuilder;
import syvenko.helpers.exceptions.UserAlreadyExistsException;
import syvenko.model.Account;
import syvenko.model.services.AccountService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Public.class)
    public Account register(@Valid @RequestBody Account account) throws UserAlreadyExistsException {
        accountService.register(account);
        return account;
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Public.class)
    public Account aboutMe(@AuthenticationPrincipal Account account){
        return account;
    }


    @ExceptionHandler(value = UserAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Error handleException(UserAlreadyExistsException exception) {
        return ErrorBuilder.userAlreadyExistsErrorBuild(exception);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ErrorBuilder.validationErrorBuild(exception);
    }

}
