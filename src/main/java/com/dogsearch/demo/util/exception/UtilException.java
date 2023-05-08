package com.dogsearch.demo.util.exception;

import com.dogsearch.demo.util.array.UtilArray;

import java.util.List;

public class UtilException {
    public static final String MESSAGE_PARAM = "{PARAM}";

    //Util
    public static final String ALREADY_EXISTS_WITH_PARAM = "Essa(e) ".concat(MESSAGE_PARAM).concat(" já existe.");
    public static final String ALREADY_REGISTERED_WITH_PARAM = "Essa(e) ".concat(MESSAGE_PARAM).concat(" já está cadastrado.");
    public static final String DONT_EXISTS_WITH_PARAM = "Essa(e) ".concat(MESSAGE_PARAM).concat(" não existe.");
    public static final String PARAMS_DONT_FILLED_TO_THE_CLASS_WITH_PARAM = "Todos os parâmetros não estão preenchidos, da classe ".concat(MESSAGE_PARAM);
    public static final String PARAM_DONT_FILLED = "Parâmetro do(a) ".concat(MESSAGE_PARAM).concat(" não preenchido.");
    public static final String THIS_PARAM_ALREADY_HAVE_THIS_PARAM = "Essa(a) ".concat(MESSAGE_PARAM).concat(" já possui esse ").concat(MESSAGE_PARAM);
    public static final String PARAM_NOT_FOUND = MESSAGE_PARAM.concat(" não encontrado(a).");
    public static final String PARAM_DO_NOT_HAVE_PARAM = MESSAGE_PARAM.concat(" não possui ").concat(MESSAGE_PARAM);

    public static void throwDefault(String exception) throws Exception {
        throw new Exception(exception);
    }

    public static void throwWithMessageBuilder(String message, String[] params) throws Exception {
        throwDefault(UtilException.exceptionMessageBuilder(
                message,
                UtilArray.createList(params)
        ));
    }

    public static String exceptionMessageBuilder(String message, List<String> params) {
        List<String> messageWords = List.of(message.split(" "));
        String messageCreated = "";
        for(String word : messageWords) {
            if (word.equals(MESSAGE_PARAM)) {
                messageCreated+= params.remove(0).concat(" ");
            } else {
                messageCreated+= word.concat((" "));
            }
        }
        return messageCreated;
    }
}
