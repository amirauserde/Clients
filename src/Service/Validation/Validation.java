package Service.Validation;

import Service.Exception.ValidationException;

@FunctionalInterface
public interface Validation <T> {
    void validate(T t) throws ValidationException;
}
