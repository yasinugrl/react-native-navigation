package com.reactnativenavigation.options.parsers;

import com.reactnativenavigation.options.params.NullIntParam;
import com.reactnativenavigation.options.params.IntParam;

import org.json.JSONObject;

public class NumberParser {
    public static IntParam parse(JSONObject json, String number) {
        return json.has(number) ? new IntParam(json.optInt(number)) : NullIntParam.INSTANCE;
    }
}
