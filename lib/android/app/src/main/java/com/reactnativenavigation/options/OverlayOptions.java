package com.reactnativenavigation.options;

import com.reactnativenavigation.options.params.BoolParam;
import com.reactnativenavigation.options.params.NullBoolParam;
import com.reactnativenavigation.options.parsers.BoolParser;

import org.json.JSONObject;

public class OverlayOptions {
    public BoolParam interceptTouchOutside = NullBoolParam.INSTANCE;

    public static OverlayOptions parse(JSONObject json) {
        OverlayOptions options = new OverlayOptions();
        if (json == null) return options;

        options.interceptTouchOutside = BoolParser.parse(json,"interceptTouchOutside");
        return options;
    }
}
