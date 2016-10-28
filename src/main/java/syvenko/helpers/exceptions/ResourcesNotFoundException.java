package syvenko.helpers.exceptions;

public class ResourcesNotFoundException extends Exception {
    public ResourcesNotFoundException() {
        super("Resource not found");
    }
}
