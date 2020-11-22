package com.reactnativenavigation;

import androidx.annotation.VisibleForTesting;

import com.facebook.jni.HybridData;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.turbomodule.core.ReactPackageTurboModuleManagerDelegate;
import com.facebook.soloader.SoLoader;

import java.util.List;

public class RNNTurboModuleManagerDelegate extends ReactPackageTurboModuleManagerDelegate {
    static {
        SoLoader.loadLibrary("rnn");
    }

    protected native HybridData initHybrid();

    @VisibleForTesting
    native boolean canCreateTurboModule(String moduleName);

    public RNNTurboModuleManagerDelegate(ReactApplicationContext context, List<ReactPackage> packages) {
        super(context, packages);
    }
}
