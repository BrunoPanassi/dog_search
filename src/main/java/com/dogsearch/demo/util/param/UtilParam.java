package com.dogsearch.demo.util.param;

import com.dogsearch.demo.util.exception.UtilException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilParam {
    public static void checkIfAllParamsAreFilled(String[] params, String fromWhereAreBeingValidated) throws Exception {
        long paramsCount = params.length;
        List<String> exceptionMessageParams = new ArrayList<>();
        exceptionMessageParams.add(fromWhereAreBeingValidated);
        boolean allParamsAreFilled = paramsCount == howManyParamsAreFilled(params);
        if (!allParamsAreFilled) {
            UtilException.throwDefault(
                    UtilException.exceptionMessageBuilder(
                            UtilException.PARAMS_DONT_FILLED_TO_THE_CLASS_WITH_PARAM,
                            exceptionMessageParams
                    )
            );
        }
    }

    private static long howManyParamsAreFilled(String[] params) {
        return Arrays.stream(params).filter(p -> p.length() > 0).count();
    }
}
