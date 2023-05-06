package com.dogsearch.demo.util.param;

import com.dogsearch.demo.util.exception.UtilException;
import java.util.List;

public class UtilParam {

    public static final Long DEFAULT_LONG_PARAM_TO_REPO = 0L;
    public static final String DEFAULT_STRING_PARAM_TO_REPO = "_default_";

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
