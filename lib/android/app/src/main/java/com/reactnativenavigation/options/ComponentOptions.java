package com.reactnativenavigation.options;

import com.reactnativenavigation.options.params.BoolParam;
import com.reactnativenavigation.options.params.NullBoolParam;
import com.reactnativenavigation.options.params.NullIntParam;
import com.reactnativenavigation.options.params.NullStringParam;
import com.reactnativenavigation.options.params.IntParam;
import com.reactnativenavigation.options.params.StringParam;
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

    public StringParam name = NullStringParam.INSTANCE;
    public StringParam componentId = NullStringParam.INSTANCE;
    public Alignment alignment = Alignment.Default;
    public BoolParam waitForRender = NullBoolParam.INSTANCE;
    public IntParam width = NullIntParam.INSTANCE;
    public IntParam height = NullIntParam.INSTANCE;

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
        name = NullStringParam.INSTANCE;
        componentId = NullStringParam.INSTANCE;
        alignment = Alignment.Default;
        waitForRender = NullBoolParam.INSTANCE;
        width = NullIntParam.INSTANCE;
        height = NullIntParam.INSTANCE;
    }
}
