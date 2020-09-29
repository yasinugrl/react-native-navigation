package com.reactnativenavigation.options.parsers;

import android.content.Context;

import com.reactnativenavigation.options.params.NullText;
import com.reactnativenavigation.options.params.Text;

import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nullable;

public class IconParser {
    private static Integer getSystemResourceId(String systemIcon, Context context) {
        return context.getResources().getIdentifier(systemIcon, "drawable", "android");
    }

    public static Text parse(@Nullable JSONObject json, String key, Context context) {
        if (json == null || !json.has(key)) return new NullText();
        try {
            if (!TextParser.parse(json.optJSONObject(key), "system").hasValue()) {
                Text fallbackIcon = TextParser.parse(json.optJSONObject(key), "fallback");
                Text systemIcon = TextParser.parse(json.optJSONObject(key), "system");
                if (getSystemResourceId(systemIcon.toString(), context) > 0) {
                    return new Text("@android:drawable/" + systemIcon);
                } else {
                    if (!fallbackIcon.hasValue()) {
                        JSONObject js = json.optJSONObject(key);
                        if (js != null) {
                            return js.get("fallback") instanceof String ? TextParser.parse(js, "fallback") : TextParser.parse(js.optJSONObject("fallback"), "uri");
                        } else {
                            return new NullText();
                        }
                    }
                }
            }
            return json.get(key) instanceof String ? TextParser.parse(json, key) : TextParser.parse(json.optJSONObject(key), "uri");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new NullText();
    }
}
