package com.reactnativenavigation.options;

import android.content.Context;

import com.reactnativenavigation.options.params.BoolParam;
import com.reactnativenavigation.options.params.Colour;
import com.reactnativenavigation.options.params.NullBoolParam;
import com.reactnativenavigation.options.params.NullColor;
import com.reactnativenavigation.options.params.NullIntParam;
import com.reactnativenavigation.options.params.IntParam;
import com.reactnativenavigation.options.parsers.BoolParser;
import com.reactnativenavigation.options.parsers.ColorParser;
import com.reactnativenavigation.options.parsers.NumberParser;

import org.json.JSONObject;

import androidx.annotation.Nullable;

public class DotIndicatorOptions {
    public static DotIndicatorOptions parse(Context context, @Nullable JSONObject json) {
        DotIndicatorOptions options = new DotIndicatorOptions();
        if (json == null) return options;

        options.color = ColorParser.parse(context, json, "color");
        options.size = NumberParser.parse(json, "size");
        options.visible = BoolParser.parse(json, "visible");
        options.animate = BoolParser.parse(json, "animate");

        return options;
    }

    public Colour color = NullColor.INSTANCE;
    public IntParam size = NullIntParam.INSTANCE;
    public BoolParam visible = NullBoolParam.INSTANCE;
    public BoolParam animate = NullBoolParam.INSTANCE;

    public boolean hasValue() {
        return visible.hasValue();
    }
}
