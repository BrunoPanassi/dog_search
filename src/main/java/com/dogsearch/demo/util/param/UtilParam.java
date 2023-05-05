package com.dogsearch.demo.util.param;

import com.dogsearch.demo.util.exception.UtilException;
import java.util.List;

public class UtilParam {

    public static final String PARAM_HAS_BEEN_SAVED = "O(a) ".concat(UtilException.MESSAGE_PARAM).concat(" foi salvo com sucesso.");

    public static void checkIfAllParamsAreFilled(List<String> params, String fromWhereAreBeingValidated) throws Exception {
        long paramsCount = params.size();
        boolean allParamsAreFilled = paramsCount == howManyParamsAreFilled(params);
        if (!allParamsAreFilled) {
            UtilParam.throwAllParamsAreNotFilled(fromWhereAreBeingValidated);
        }
    }

    public static void throwAllParamsAreNotFilled(String fromWhere) throws Exception {
        String[] exceptionMessageParams = {fromWhere};
        UtilException.throwWithMessageBuilder(UtilException.PARAMS_DONT_FILLED_TO_THE_CLASS_WITH_PARAM, exceptionMessageParams);
    }

    private static long howManyParamsAreFilled(List<String> params) {
        return params.stream().filter(p -> p.length() > 0).count();
    }
}
