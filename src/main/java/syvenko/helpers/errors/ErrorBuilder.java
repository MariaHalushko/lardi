package syvenko.helpers.errors;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import syvenko.helpers.exceptions.ResourcesNotFoundException;
import syvenko.helpers.exceptions.UserAlreadyExistsException;

public class ErrorBuilder {
    public static Error validationErrorBuild(MethodArgumentNotValidException e) {
        Errors errors = e.getBindingResult();
        Error error = new Error("Count: " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addError(objectError.getDefaultMessage());
        }
        return error;
    }

    public static Error userAlreadyExistsErrorBuild(UserAlreadyExistsException e) {
        Error error = new Error("Count: 1 error(s)");
        error.addError(e.getMessage());
        return error;
    }

    public static Error resourceNotFoundErrorBuild(ResourcesNotFoundException e) {
        Error error = new Error("Count: 1 error(s)");
        error.addError(e.getMessage());
        return error;
    }
}
