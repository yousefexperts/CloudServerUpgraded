package com.experts.core.biller.statemachine.api.util;


import java.util.function.Consumer;

public class Validator {
    private ValidationException.Warns warns = ValidationException.Warns.init();

    public static void validate(Consumer<Validator> proc) {
        Validator validator = new Validator();
        proc.accept(validator);
        validator.verify();
    }


    public Validator check(boolean valid, String message) {
        if (!valid)
            warns.add(message);
        return this;
    }


    public Validator checkField(boolean valid, String field, String message) {
        if (!valid)
            warns.add(field, message);
        return this;
    }


    public Validator verify(boolean valid, String message) {
        return check(valid, message).verify();
    }

    public Validator verifyField(boolean valid, String field, String message) {
        return checkField(valid, field, message).verify();
    }

    public Validator verify() {
        if (hasWarn())
            throw new ValidationException(warns);
        return clear();
    }

    public boolean hasWarn() {
        return warns.nonEmpty();
    }

    public Validator clear() {
        warns.list().clear();
        return this;
    }

}
