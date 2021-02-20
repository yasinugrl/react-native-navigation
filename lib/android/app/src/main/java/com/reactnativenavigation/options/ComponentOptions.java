package com.reactnativenavigation.options;

import com.reactnativenavigation.options.params.BoolProp;
import com.reactnativenavigation.options.params.NoValBool;
import com.reactnativenavigation.options.params.NoValInt;
import com.reactnativenavigation.options.params.NullTextProp;
import com.reactnativenavigation.options.params.IntProp;
import com.reactnativenavigation.options.params.TextProp;
import com.reactnativenavigation.options.parsers.BoolParser;
import com.reactnativenavigation.options.parsers.NumberParser;
import com.reactnativenavigation.options.parsers.TextParser;

import org.json.JSONObject;

public class ComponentOptions {
    public static ComponentOptions parse(JSONObject json) {
        ComponentOptions result = new ComponentOptions();
        if (json == null) return result;

        result.name = TextParser.parse(json, "name");
        result.componentId = TextParser.parse(json, "componentId");
        result.alignment = Alignment.fromString(TextParser.parse(json, "alignment").get(""));
        result.waitForRender = BoolParser.parse(json, "waitForRender");
        result.width = NumberParser.parse(json, "width");
        result.height = NumberParser.parse(json, "height");

        return result;
    }

    public TextProp name = NullTextProp.INSTANCE;
    public TextProp componentId = NullTextProp.INSTANCE;
    public Alignment alignment = Alignment.Default;
    public BoolProp waitForRender = NoValBool.INSTANCE;
    public IntProp width = NoValInt.INSTANCE;
    public IntProp height = NoValInt.INSTANCE;

    void mergeWith(ComponentOptions other) {
        if (other.componentId.hasValue()) componentId = other.componentId;
        if (other.name.hasValue()) name = other.name;
        if (other.waitForRender.hasValue()) waitForRender = other.waitForRender;
        if (other.alignment != Alignment.Default) alignment = other.alignment;
        if (other.width.hasValue()) width = other.width;
        if (other.height.hasValue()) height = other.height;
    }

    public void mergeWithDefault(ComponentOptions defaultOptions) {
        if (!componentId.hasValue()) componentId = defaultOptions.componentId;
        if (!name.hasValue()) name = defaultOptions.name;
        if (!waitForRender.hasValue()) waitForRender = defaultOptions.waitForRender;
        if (alignment == Alignment.Default) alignment = defaultOptions.alignment;
        if (!width.hasValue()) width = defaultOptions.width;
        if (!height.hasValue()) height = defaultOptions.height;
    }

    public boolean hasValue() {
        return name.hasValue();
    }

    public boolean equals(ComponentOptions other) {
        return name.equals(other.name) &&
                componentId.equals(other.componentId) &&
                alignment.equals(other.alignment) &&
                waitForRender.equals(other.waitForRender) &&
                width.equals(other.width) &&
                height.equals(other.height);
    }

    public void reset() {
        name = NullTextProp.INSTANCE;
        componentId = NullTextProp.INSTANCE;
        alignment = Alignment.Default;
        waitForRender = NoValBool.INSTANCE;
        width = NoValInt.INSTANCE;
        height = NoValInt.INSTANCE;
    }
}
