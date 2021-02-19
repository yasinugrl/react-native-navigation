package com.reactnativenavigation.options.parsers;

import com.reactnativenavigation.options.params.NullStringParam;
import com.reactnativenavigation.options.params.StringParam;
import com.reactnativenavigation.utils.ObjectUtils;

import org.json.JSONObject;

import javax.annotation.*;

public class TextParser {
    public static StringParam parse(@Nullable JSONObject json, String text, String defaultValue) {
        return ObjectUtils.take(parse(json, text), new StringParam(defaultValue));
    }

    public static StringParam parse(@Nullable JSONObject json, String text) {
        return json != null && json.has(text) ? new StringParam(json.optString(text)) : NullStringParam.INSTANCE;
    }
}
