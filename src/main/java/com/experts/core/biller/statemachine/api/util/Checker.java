package com.experts.core.biller.statemachine.api.util;

public abstract class Checker {

    public static boolean match(String regex, Object v) {
        return v != null ? v.toString().matches(regex) : true;
    }

    public static boolean len(String v, int max) {
        return wordSize(v) <= max;
    }

    private static int wordSize(String v) {
        return v.codePointCount(0, v.length());
    }
}
