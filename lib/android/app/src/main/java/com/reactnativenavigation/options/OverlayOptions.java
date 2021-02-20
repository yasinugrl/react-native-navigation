package com.reactnativenavigation.options;

import com.reactnativenavigation.options.params.BoolProp;
import com.reactnativenavigation.options.params.NoValBool;
import com.reactnativenavigation.options.parsers.BoolParser;

import org.json.JSONObject;

public class OverlayOptions {
    public BoolProp interceptTouchOutside = NoValBool.INSTANCE;

    public static OverlayOptions parse(JSONObject json) {
        OverlayOptions options = new OverlayOptions();
        if (json == null) return options;

        options.interceptTouchOutside = BoolParser.parse(json,"interceptTouchOutside");
        return options;
    }
}
