package com.reactnativenavigation.options;

import android.graphics.Typeface;
import androidx.annotation.Nullable;

import com.reactnativenavigation.options.params.NullStringParam;
import com.reactnativenavigation.options.params.StringParam;
import com.reactnativenavigation.options.parsers.TextParser;
import com.reactnativenavigation.options.parsers.TypefaceLoader;

import org.json.JSONObject;

public class TopTabOptions {
    public StringParam title = NullStringParam.INSTANCE;
    @Nullable public Typeface fontFamily;
    public int tabIndex;

    public static TopTabOptions parse(TypefaceLoader typefaceManager, JSONObject json) {
        TopTabOptions result = new TopTabOptions();
        if (json == null) return result;

        result.title = TextParser.parse(json, "title");
        result.fontFamily = typefaceManager.getTypeFace(json.optString("titleFontFamily"), null, null);
        return result;
    }

    void mergeWith(TopTabOptions other) {
        if (other.title.hasValue()) title = other.title;
        if (other.fontFamily != null) fontFamily = other.fontFamily;
        if (other.tabIndex >= 0) tabIndex = other.tabIndex;
    }

    void mergeWithDefault(TopTabOptions other) {
        if (fontFamily == null) fontFamily = other.fontFamily;
    }
}
