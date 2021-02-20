package com.reactnativenavigation.utils;

import com.reactnativenavigation.options.Options;
import com.reactnativenavigation.options.params.TextProp;

import java.util.ArrayList;

public class OptionHelper {
    public static Options createBottomTabOptions() {
        Options options = new Options();
        options.topBar.buttons.left = new ArrayList<>();
        options.bottomTabOptions.text = new TextProp("Tab");
        options.bottomTabOptions.icon = new TextProp("http://127.0.0.1/icon.png");
        return options;
    }
}
