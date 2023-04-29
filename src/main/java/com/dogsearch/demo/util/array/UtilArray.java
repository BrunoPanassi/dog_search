package com.dogsearch.demo.util.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilArray {
    public static List<String> createList(String[] param) {
        return new ArrayList<>(Arrays.asList(param));
    }
}
