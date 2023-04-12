package com.dogsearch.demo.util.param;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilParam {
    public static boolean checkIfAllParamsAreFilled(String[] params) {
        long keysCount = params.length;
        return keysCount == howManyParamsAreFilled(params);
    }

    private static long howManyParamsAreFilled(String[] params) {
        return Arrays.stream(params).filter(p -> p.length() > 0).count();
    }
}
