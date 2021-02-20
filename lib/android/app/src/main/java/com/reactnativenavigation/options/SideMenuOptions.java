package com.reactnativenavigation.options;

import com.reactnativenavigation.options.params.BoolProp;
import com.reactnativenavigation.options.params.NoValBool;
import com.reactnativenavigation.options.params.NoValInt;
import com.reactnativenavigation.options.params.IntProp;
import com.reactnativenavigation.options.parsers.BoolParser;
import com.reactnativenavigation.options.parsers.NumberParser;

import org.json.JSONObject;

public class SideMenuOptions {
    public BoolProp visible = NoValBool.INSTANCE;
    public BoolProp animate = NoValBool.INSTANCE;
    public BoolProp enabled = NoValBool.INSTANCE;
    public IntProp height = NoValInt.INSTANCE;
    public IntProp width = NoValInt.INSTANCE;

    public static SideMenuOptions parse(JSONObject json) {
        SideMenuOptions options = new SideMenuOptions();
        if (json == null) return options;

        options.visible = BoolParser.parse(json, "visible");
        options.animate = BoolParser.parse(json, "animate");
        options.enabled = BoolParser.parse(json, "enabled");
        options.height = NumberParser.parse(json, "height");
        options.width = NumberParser.parse(json, "width");

        return options;
    }

    public void mergeWith(SideMenuOptions other) {
        if (other.visible.hasValue()) visible = other.visible;
        if (other.animate.hasValue()) animate = other.animate;
        if (other.enabled.hasValue()) enabled = other.enabled;
        if (other.height.hasValue()) height = other.height;
        if (other.width.hasValue()) width = other.width;
    }

    public void mergeWithDefault(SideMenuOptions defaultOptions) {
        if (!visible.hasValue()) visible = defaultOptions.visible;
        if (!animate.hasValue()) animate = defaultOptions.animate;
        if (!enabled.hasValue()) enabled = defaultOptions.enabled;
        if (!height.hasValue()) height = defaultOptions.height;
        if (!width.hasValue()) width = defaultOptions.width;
    }
}
