package com.reactnativenavigation.viewcontrollers.stack.topbar.button;

import com.reactnativenavigation.options.Options;
import com.reactnativenavigation.options.params.Bool;
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController;

public class BackButtonHelper {
    public void clear(ViewController child) {
        if (!child.getOptions().topBar.buttons.back.hasValue()) {
            child.getOptions().topBar.buttons.back.visible = new Bool(false);
        }
    }

    public void addToPushedChild(ViewController child) {
        if (child.getOptions().topBar.buttons.left != null || child.getOptions().topBar.buttons.back.visible.isFalse()) return;
        Options options = new Options();
        options.topBar.buttons.back.setVisible();
        child.mergeOptions(options);
    }
}
