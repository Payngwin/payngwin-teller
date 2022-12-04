package com.mungwin.payngwinteller.exception;

public class Precondition {
    @FunctionalInterface
    public interface CheckerFunction {
        boolean doCheckValue();
    }
    public static void check(CheckerFunction checker, RuntimeException toThrow) {
        if(!checker.doCheckValue()) {
            throw toThrow;
        }
    }
    public static void check(boolean check, RuntimeException toThrow) {
        if(!check) {
            throw toThrow;
        }
    }
}
