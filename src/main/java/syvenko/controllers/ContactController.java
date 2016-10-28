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
import syvenko.helpers.exceptions.ResourcesNotFoundException;
import syvenko.helpers.exceptions.UserAlreadyExistsException;
import syvenko.model.Account;
import syvenko.model.Contact;
import syvenko.model.services.ContactService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("api/v1/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Public.class)
    public List<Contact> getAll(@RequestParam(value = "firstName",required = false) String firstName,
                                @RequestParam(value = "lastName",required = false) String lastName,
                                @RequestParam(value = "phoneNumber",required = false) String phoneNumber,
                                @AuthenticationPrincipal Account account){
        return contactService.getList(firstName,lastName,phoneNumber,account);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Public.class)
    public Contact add(@Valid @RequestBody Contact contact, @AuthenticationPrincipal Account account){
        return contactService.createEntry(contact, account);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Public.class)
    public Contact getOne(@PathVariable Long id, @AuthenticationPrincipal Account account) throws ResourcesNotFoundException {
        return contactService.getOne(id, account);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Public.class)
    public Contact update(@PathVariable Long id, @Valid @RequestBody Contact contact, @AuthenticationPrincipal Account account) throws ResourcesNotFoundException {
        return contactService.update(id, contact, account);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id, @AuthenticationPrincipal Account account) throws IOException, UserAlreadyExistsException, ResourcesNotFoundException {
        contactService.delete(id, account);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error handleException(MethodArgumentNotValidException exception) {
        return ErrorBuilder.validationErrorBuild(exception);
    }

    @ExceptionHandler(value = ResourcesNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Error handleException(ResourcesNotFoundException exception) {
        return ErrorBuilder.resourceNotFoundErrorBuild(exception);
    }
}
