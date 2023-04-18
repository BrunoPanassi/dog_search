package com.dogsearch.demo.util.exception;

import java.util.List;

public class UtilException {
    public static final String MESSAGE_PARAM = "{PARAM}";

    //Util
    public static final String ALREADY_EXISTS_WITH_PARAM = "Essa(e)".concat(MESSAGE_PARAM).concat(" já existe.");
    public static final String DONT_EXISTS_WITH_PARAM = "Essa(e)".concat(MESSAGE_PARAM).concat(" não existe.");

    //User
    public static final String USER_ALREADY_EXISTS = "Esse usuário já existe";
    public static final String USER_DONT_EXISTS = "Usuário não existe;";

    public static void throwDefault(String exception) throws Exception {
        throw new Exception(exception);
    }

    public static String exceptionMessageBuilder(String message, List<String> params) {
        StringBuilder builder = new StringBuilder();
        List<String> messageWords = List.of(message.split(" "));
        for(String word : messageWords) {
            if (word.equals(MESSAGE_PARAM))
                message = message.replace(MESSAGE_PARAM, params.remove(0));
        }
        return message;
    }
}
