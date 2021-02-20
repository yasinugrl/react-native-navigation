package com.reactnativenavigation.options.parsers;

import com.reactnativenavigation.options.params.NullTextProp;
import com.reactnativenavigation.options.params.TextProp;
import com.reactnativenavigation.utils.ObjectUtils;

import org.json.JSONObject;

import javax.annotation.*;

public class TextParser {
    public static TextProp parse(@Nullable JSONObject json, String text, String defaultValue) {
        return ObjectUtils.take(parse(json, text), new TextProp(defaultValue));
    }

    public static TextProp parse(@Nullable JSONObject json, String text) {
        return json != null && json.has(text) ? new TextProp(json.optString(text)) : NullTextProp.INSTANCE;
    }
}
