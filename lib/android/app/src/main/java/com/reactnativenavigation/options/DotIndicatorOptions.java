package com.reactnativenavigation.options;

import android.content.Context;

import com.reactnativenavigation.options.params.BoolProp;
import com.reactnativenavigation.options.params.Colour;
import com.reactnativenavigation.options.params.NoValBool;
import com.reactnativenavigation.options.params.NullColor;
import com.reactnativenavigation.options.params.NoValInt;
import com.reactnativenavigation.options.params.IntProp;
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
    public IntProp size = NoValInt.INSTANCE;
    public BoolProp visible = NoValBool.INSTANCE;
    public BoolProp animate = NoValBool.INSTANCE;

    public boolean hasValue() {
        return visible.hasValue();
    }
}
