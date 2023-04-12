package com.dogsearch.demo.util.exception;

public class UtilException {
    public static final String USER_ALREADY_EXISTS = "Esse usuário já existe";
    public static final String USER_DONT_EXISTS = "Usuário não existe;";

    public static void throwDefault(String exception) throws Exception {
        throw new Exception(exception);
    }
}
