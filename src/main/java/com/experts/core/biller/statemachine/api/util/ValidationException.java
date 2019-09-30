package com.experts.core.biller.statemachine.api.util;

import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Warns warns;


    public ValidationException(String message) {
        super(message);
        warns = Warns.init(message);
    }


    public ValidationException(String field, String message) {
        super(message);
        warns = Warns.init(field, message);
    }


    public ValidationException(String field, String message, String[] messageArgs) {
        super(message);
        warns = Warns.init(field, message, messageArgs);
    }

    public ValidationException(final Warns warns) {
        super(warns.head().map((v) -> v.getMessage()).orElse(ErrorKeys.Exception));
        this.warns = warns;
    }

    public List<Warn> list() {
        return warns.list();
    }

    @Override
    public String getMessage() {
        return warns.head().map((v) -> v.getMessage()).orElse(ErrorKeys.Exception);
    }

    public static class Warns implements Serializable {
        private static final long serialVersionUID = 1L;
        private List<Warn> list = new ArrayList<>();

        private Warns() {
        }

        public Warns add(String message) {
            list.add(new Warn(null, message, null));
            return this;
        }

        public Warns add(String field, String message) {
            list.add(new Warn(field, message, null));
            return this;
        }

        public Warns add(String field, String message, String[] messageArgs) {
            list.add(new Warn(field, message, messageArgs));
            return this;
        }

        public Optional<Warn> head() {
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        }

        public List<Warn> list() {
            return list;
        }

        public boolean nonEmpty() {
            return !list.isEmpty();
        }

        public static Warns init() {
            return new Warns();
        }

        public static Warns init(String message) {
            return init().add(message);
        }

        public static Warns init(String field, String message) {
            return init().add(field, message);
        }

        public static Warns init(String field, String message, String[] messageArgs) {
            return init().add(field, message, messageArgs);
        }

    }


    @Value
    public static class Warn implements Serializable {
        private static final long serialVersionUID = 1L;

        private String field;

        private String message;

        private String[] messageArgs;


        public boolean global() {
            return field == null;
        }
    }


    public static interface ErrorKeys {

        String Exception = "error.Exception";

        String EntityNotFound = "error.EntityNotFoundException";

        String Authentication = "error.Authentication";

        String AccessDenied = "error.AccessDeniedException";


        String Login = "error.login";

        String DuplicateId = "error.duplicateId";


        String ActionUnprocessing = "error.ActionStatusType.unprocessing";
    }

}
