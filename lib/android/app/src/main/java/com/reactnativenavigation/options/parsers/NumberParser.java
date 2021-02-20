package com.reactnativenavigation.options.parsers;

import com.reactnativenavigation.options.params.NoValInt;
import com.reactnativenavigation.options.params.IntProp;

import org.json.JSONObject;

public class NumberParser {
    public static IntProp parse(JSONObject json, String number) {
        return json.has(number) ? new IntProp(json.optInt(number)) : NoValInt.INSTANCE;
    }
}
