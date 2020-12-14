package com.reactnativenavigation.options;

import android.content.Context;

import com.reactnativenavigation.options.params.Bool;
import com.reactnativenavigation.options.params.NullBool;
import com.reactnativenavigation.options.params.NullText;
import com.reactnativenavigation.options.params.Text;
import com.reactnativenavigation.options.parsers.BoolParser;
import com.reactnativenavigation.options.parsers.TextParser;
import com.reactnativenavigation.options.parsers.TypefaceLoader;

import org.json.JSONObject;

public class SearchBarOptions {
    public static SearchBarOptions parse(Context context, TypefaceLoader typefaceManager, JSONObject json) {
        SearchBarOptions options = new SearchBarOptions();

        if (json == null) return options;

        options.visible = BoolParser.parse(json, "visible");
        options.focus = BoolParser.parse(json, "focus");
        options.placeholder = TextParser.parse(json, "placeholder");

        return options;
    }

    public Bool visible = new NullBool();
    public Bool focus = new NullBool();
    public Text placeholder = new NullText();

}
