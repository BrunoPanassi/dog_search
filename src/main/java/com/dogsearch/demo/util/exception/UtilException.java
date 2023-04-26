package com.dogsearch.demo.util.exception;

import java.util.List;

public class UtilException {
    public static final String MESSAGE_PARAM = "{PARAM}";

    //Util
    public static final String ALREADY_EXISTS_WITH_PARAM = "Essa(e)".concat(MESSAGE_PARAM).concat(" já existe.");
    public static final String DONT_EXISTS_WITH_PARAM = "Essa(e)".concat(MESSAGE_PARAM).concat(" não existe.");
    public static final String PARAMS_DONT_FILLED_TO_THE_CLASS_WITH_PARAM = "Todos os parâmetros não estão preenchidos, da classe ".concat(MESSAGE_PARAM);

    //User
    public static final String USER_ALREADY_EXISTS = "Esse usuário já existe";

    public static void throwDefault(String exception) throws Exception {
        throw new Exception(exception);
    }

    public static String exceptionMessageBuilder(String message, List<String> params) {
        List<String> messageWords = List.of(message.split(" "));
        for(String word : messageWords) {
            if (word.equals(MESSAGE_PARAM)) {
                message = message.replace(MESSAGE_PARAM, params.remove(0));
            }
        }
        return message;
    }
}
